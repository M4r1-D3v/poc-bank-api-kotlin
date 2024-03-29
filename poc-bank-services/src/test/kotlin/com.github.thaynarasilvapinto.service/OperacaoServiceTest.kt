package com.example.service

import com.example.model.Cliente
import com.example.model.Conta
import com.example.model.Operacao
import com.example.model.repository.ContaRepository
import com.example.model.repository.OperacaoRepository
import com.example.service.config.ServiceBaseTest
import com.example.service.exception.AccountIsValidException
import com.example.service.exception.BalanceIsInsufficientException
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Matchers.anyString
import kotlin.test.assertNotNull

class OperacaoServiceTest : ServiceBaseTest() {

    @get:Rule
    var thrown = ExpectedException.none()


    private val repositoryConta: ContaRepository = mock()
    private val repositoryOperacao: OperacaoRepository = mock()

    private lateinit var operacaoService: OperacaoService
    private lateinit var contaService: ContaService

    private lateinit var joao: Cliente
    private lateinit var joaoConta: Conta
    private lateinit var operacaoDepositoJoao: Operacao
    private lateinit var maria: Cliente
    private lateinit var contaMaria: Conta

    @Before
    fun setup() {

        operacaoService = OperacaoService(repositoryOperacao, repositoryConta)
        contaService = ContaService(repositoryConta, repositoryOperacao)

        createClient()
        operacaoDepositoJoao = Operacao(
            contaOrigem = joaoConta,
            contaDestino = joaoConta,
            valorOperacao = 200.00,
            tipoOperacao = Operacao.TipoOperacao.DEPOSITO
        )
    }

    private fun createClient() {
        joaoConta = Conta(saldo = 200.00)
        joao = Cliente(
            nome = "Conta Test Joao Service",
            cpf = "151.425.426-75",
            conta = joaoConta
        )

        contaMaria = Conta(saldo = 0.00)
        maria = Cliente(
            nome = "Conta Test Maria Service",
            cpf = "086.385.420-62",
            conta = contaMaria
        )
    }

    @Test
    fun buscar() {
        whenever(repositoryOperacao.findById(operacaoDepositoJoao.idOperacao)).thenReturn(operacaoDepositoJoao)
        operacaoService.find(operacaoDepositoJoao.idOperacao)
        verify(repositoryOperacao, times(1)).findById(operacaoDepositoJoao.idOperacao)
    }


    @Test
    fun `Nao pode se pode sacar um valor mais alto que o saldo`() {

        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)

        thrown.expect(BalanceIsInsufficientException::class.java)
        thrown.expectMessage("Saldo Insuficiente")

        operacaoService.saque(
            id = joaoConta.id,
            valor = 1000000.00
        )
    }

    @Test
    fun `Nao pode ser possivel realizar transferencia quando o saldo na conta e insuficiente`() {
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)
        whenever(repositoryConta.findById(contaMaria.id)).thenReturn(contaMaria)

        thrown.expect(BalanceIsInsufficientException::class.java)
        thrown.expectMessage("Saldo Insuficiente")

        operacaoService.transferencia(
            id = joaoConta.id,
            idDestino = contaMaria.id,
            valor = 10000.00
        )
    }

    @Test
    fun `Ao solicitar transferwncia tanto a conta de destino quanto a de origem devem ser validas`() {
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)
        whenever(repositoryConta.findById(contaMaria.id)).thenReturn(contaMaria)

        thrown.expect(AccountIsValidException::class.java)
        thrown.expectMessage("As contas devem ser validas")

        operacaoService.transferencia(
            id = "-1",
            idDestino = "-2",
            valor = 10.00
        )
    }

    @Test
    fun `Ao solicitar transferencia a conta de origem deve ser valida`() {
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)
        whenever(repositoryConta.findById(contaMaria.id)).thenReturn(contaMaria)

        thrown.expect(AccountIsValidException::class.java)
        thrown.expectMessage("As contas devem ser validas")

        operacaoService.transferencia(
            id = "-1",
            idDestino = contaMaria.id,
            valor = 10.00
        )
    }

    @Test
    fun `Ao solicitar transferencia a conta de destino deve ser valida`() {
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)
        whenever(repositoryConta.findById(contaMaria.id)).thenReturn(contaMaria)

        thrown.expect(AccountIsValidException::class.java)
        thrown.expectMessage("As contas devem ser validas")

        operacaoService.transferencia(
            id = joaoConta.id,
            idDestino = "-2",
            valor = 100.00
        )
    }

    @Test
    fun `Nao pode ser possivel realizar uma transferencia para voce mesmo`() {
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)
        whenever(repositoryConta.findById(contaMaria.id)).thenReturn(contaMaria)

        thrown.expect(AccountIsValidException::class.java)
        thrown.expectMessage("Não pode efetuar uma transferencia para você mesmo")

        operacaoService.transferencia(
            id = joaoConta.id,
            idDestino = joaoConta.id,
            valor = 100.00
        )
    }

    @Test
    fun `Nao pode ser possivel realizar um deposito quando a conta e invalida`() {
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)

        thrown.expect(AccountIsValidException::class.java)
        thrown.expectMessage("A conta deve ser valida")

        operacaoService.deposito(
            id = "-1",
            valor = 100.00
        )
    }

    @Test
    fun `Nao pode ser possivel realizar um saque quando a conta e invalida`() {
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)

        thrown.expect(AccountIsValidException::class.java)
        thrown.expectMessage("A conta deve ser valida")

        operacaoService.saque(
            id = "-1",
            valor = 10.00
        )
    }

    @Test
    fun `deve realizar deposito`() {
        val deposito = Operacao(
            valorOperacao = 100.00,
            tipoOperacao = Operacao.TipoOperacao.DEPOSITO,
            contaDestino = joaoConta,
            contaOrigem = joaoConta
        )

        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)

        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)
        joaoConta.saldo = 100.00
        whenever(repositoryConta.update(joaoConta)).thenReturn(1)
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)

        whenever(repositoryOperacao.save(any())).thenReturn(1)
        whenever(repositoryOperacao.findById(anyString())).thenReturn(deposito)

        val resultDeposito = operacaoService.deposito(
            id = joaoConta.id,
            valor = 100.00
        )

        assertNotNull(resultDeposito)
        assertEquals(deposito.idOperacao, resultDeposito.idOperacao)
    }

    @Test
    fun `deve realizar saque`() {
        val saque = Operacao(valorOperacao = 100.00,
            tipoOperacao = Operacao.TipoOperacao.SAQUE,
            contaDestino = joaoConta,
            contaOrigem = joaoConta)

        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)
        joaoConta.saldo = joaoConta.saldo - 100.00
        whenever(repositoryConta.update(joaoConta)).thenReturn(1)
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)

        whenever(repositoryOperacao.save(any())).thenReturn(1)
        whenever(repositoryOperacao.findById(anyString())).thenReturn(saque)

        val resultSaque = operacaoService.saque(
            id = joaoConta.id,
            valor = 100.00
        )
        assertNotNull(saque)
        assertEquals(saque.idOperacao, resultSaque!!.idOperacao)
    }
    @Test
    fun `deve realizar transferencia`() {
        val transferencia = Operacao(valorOperacao = 100.00,
            tipoOperacao = Operacao.TipoOperacao.TRANSFERENCIA,
            contaDestino = contaMaria,
            contaOrigem = joaoConta)
        val transferenciaRecebimento = Operacao(valorOperacao = 100.00,
            tipoOperacao = Operacao.TipoOperacao.RECEBIMENTO_TRANSFERENCIA,
            contaDestino = contaMaria,
            contaOrigem = joaoConta)

        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)
        whenever(repositoryConta.findById(contaMaria.id)).thenReturn(contaMaria)

        joaoConta.saldo = joaoConta.saldo - 100.00
        whenever(repositoryConta.update(joaoConta)).thenReturn(1)
        whenever(repositoryConta.findById(joaoConta.id)).thenReturn(joaoConta)

        contaMaria.saldo = contaMaria.saldo + 100.00
        whenever(repositoryConta.update(contaMaria)).thenReturn(1)
        whenever(repositoryConta.findById(contaMaria.id)).thenReturn(contaMaria)

        whenever(repositoryOperacao.save(any())).thenReturn(1)
        whenever(repositoryOperacao.findById(anyString())).thenReturn(transferenciaRecebimento)

        whenever(repositoryOperacao.save(any())).thenReturn(1)
        whenever(repositoryOperacao.findById(anyString())).thenReturn(transferencia)

        val resultTransferencia = operacaoService.transferencia(
            id = joaoConta.id,
            idDestino = contaMaria.id,
            valor = 100.00
        )
        assertNotNull(transferencia)
        assertEquals(transferencia.idOperacao, resultTransferencia.idOperacao)
    }
}
