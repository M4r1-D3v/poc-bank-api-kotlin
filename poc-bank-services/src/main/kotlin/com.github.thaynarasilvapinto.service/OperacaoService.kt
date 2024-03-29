package com.example.service

import com.example.model.Conta
import com.example.model.Operacao
import com.example.model.repository.ContaRepository
import com.example.model.repository.OperacaoRepository
import com.example.service.exception.AccountIsValidException
import com.example.service.exception.BalanceIsInsufficientException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class OperacaoService @Autowired constructor(
    private var repo: OperacaoRepository,
    private var repoConta: ContaRepository
) {


    fun find(id: String): Operacao? {
        return repo.findById(id)
    }

    fun insert(obj: Operacao): Operacao? {
        repo.save(obj)
        return repo.findById(obj.idOperacao)
    }


    fun delete(id: String) {
        find(id)
        repo.deleteById(id)
    }

    fun findAllContaOrigem(conta: Conta) = repo.findAllByContaOrigem(conta.id)

    fun saque(valor: Double, id: String): Operacao? {

        val conta = findConta(id)

        if (conta != null) {
            if (valor <= conta.saldo) {

                var saque = Operacao(
                    contaOrigem = conta,
                    contaDestino = conta,
                    valorOperacao = valor,
                    tipoOperacao = Operacao.TipoOperacao.SAQUE
                )

                conta.saldo = conta.saldo - saque.valorOperacao

                updateConta(conta)

                saque = insert(saque)!!

                return saque
            } else
                throw BalanceIsInsufficientException(message = "Saldo Insuficiente")
        }
        throw AccountIsValidException(message = "A conta deve ser valida")
    }

    fun deposito(valor: Double, id: String): Operacao {

        val conta = findConta(id)

        if (conta != null) {

            var deposito = Operacao(
                contaOrigem = conta,
                contaDestino = conta,
                valorOperacao = valor,
                tipoOperacao = Operacao.TipoOperacao.DEPOSITO
            )

            conta.saldo = conta.saldo + deposito.valorOperacao

            updateConta(conta)

            deposito = insert(deposito)!!

            return deposito
        }
        throw AccountIsValidException(message = "A conta deve ser valida")
    }

    fun transferencia(valor: Double, id: String, idDestino: String): Operacao {

        val contaOrigem = findConta(id)
        val contaDestino = findConta(idDestino)

        if (id != idDestino) {
            if (contaOrigem != null && contaDestino != null) {
                if (valor <= contaOrigem.saldo) {

                    var recebimentoTransferencia = Operacao(
                        contaOrigem = contaOrigem,
                        contaDestino = contaDestino,
                        valorOperacao = valor,
                        tipoOperacao = Operacao.TipoOperacao.RECEBIMENTO_TRANSFERENCIA
                    )
                    var efetuarTransferencia = Operacao(
                        contaOrigem = contaOrigem,
                        contaDestino = contaDestino,
                        valorOperacao = valor,
                        tipoOperacao = Operacao.TipoOperacao.TRANSFERENCIA
                    )

                    contaOrigem.saldo = contaOrigem.saldo - efetuarTransferencia.valorOperacao
                    contaDestino.saldo = contaDestino.saldo + recebimentoTransferencia.valorOperacao

                    updateConta(contaOrigem)
                    updateConta(contaDestino)

                    efetuarTransferencia = insert(efetuarTransferencia)!!
                    insert(recebimentoTransferencia)

                    return efetuarTransferencia

                } else
                    throw BalanceIsInsufficientException(message = "Saldo Insuficiente")
            }
            throw AccountIsValidException(message = "As contas devem ser validas")
        }
        throw AccountIsValidException(message = "Não pode efetuar uma transferencia para você mesmo")
    }


    fun findConta(id: String): Conta? {
        return repoConta.findById(id)
    }
    fun updateConta(conta: Conta): Conta? {
        find(conta.id)
        repoConta.update(conta)
        return repoConta.findById(conta.id)
    }
}