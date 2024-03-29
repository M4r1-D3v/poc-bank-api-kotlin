package com.example.repositories.repository

import com.example.model.Conta
import com.example.model.Operacao
import com.example.model.repository.ContaRepository
import com.example.model.repository.OperacaoRepository
import com.example.repositories.config.RepositoryBaseTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class JdbcOperacaoTest : RepositoryBaseTest() {
    @Autowired
    private lateinit var repositoryConta: ContaRepository
    @Autowired
    private lateinit var repositoryOperacao: OperacaoRepository

    private var accountId: String = "-1"
    private var operacaoId: String = "-1"

    @Before
    fun setup() {
        operacaoId = saveACliente()
        accountId = repositoryConta.findById(repositoryOperacao.findById(operacaoId)!!.contaOrigem.id)!!.id
    }

    @After
    fun tearDown() {
        repositoryOperacao.deleteById(operacaoId)
        repositoryConta.deleteById(accountId)
    }

    private fun saveACliente(): String {

        val accountJoao = Conta(
            saldo = 0.00
        )

        val operacaoDeposito = Operacao(
            valorOperacao = 100.00,
            tipoOperacao = Operacao.TipoOperacao.DEPOSITO,
            contaOrigem = accountJoao,
            contaDestino = accountJoao
        )

        assertEquals(1, repositoryConta.save(accountJoao))
        assertEquals(1, repositoryOperacao.save(operacaoDeposito))
        return operacaoDeposito.idOperacao
    }


    @Test
    fun `deve encontrar uma operacao ja criado pelo id`() {
        val operacao = repositoryOperacao.findById(operacaoId)
        assertNotNull(operacao)
        assertEquals(operacaoId, operacao!!.idOperacao)
    }
    @Test
    fun `nao deve encontrar uma operacao nao criada pelo id`() {
        val operacao = repositoryOperacao.findById("-1")
        assertNull(operacao)
    }

    @Test
    fun `deve criar uma operacao`() {
        val account = Conta(
            saldo = 0.00
        )
        val operacaoDeposito = Operacao(
            valorOperacao = 100.00,
            tipoOperacao = Operacao.TipoOperacao.DEPOSITO,
            contaOrigem = account,
            contaDestino = account
        )
        assertEquals(1, repositoryConta.save(account))
        assertEquals(1, repositoryOperacao.save(operacaoDeposito))

        repositoryOperacao.deleteById(operacaoDeposito.idOperacao)
        repositoryConta.deleteById(account.id)
    }


    @Test
    fun `deve deletar em uma operacao ja criada`() {

        val account = Conta(
            saldo = 0.00
        )
        val operacaoDeposito = Operacao(
            valorOperacao = 100.00,
            tipoOperacao = Operacao.TipoOperacao.DEPOSITO,
            contaOrigem = account,
            contaDestino = account
        )

        repositoryConta.save(account)
        repositoryOperacao.save(operacaoDeposito)

        assertEquals(1, repositoryOperacao.deleteById(operacaoDeposito.idOperacao))
        assertEquals(1, repositoryConta.deleteById(account.id))

    }

    @Test
    fun `deve encontrar uma operacao ja criado pelo conta de origem`() {

        var operacao = repositoryOperacao.findById(operacaoId)

        val operacaoListOrigem = repositoryOperacao.findAllByContaOrigem(operacao!!.contaOrigem.id)
        assertNotNull(operacaoListOrigem)
        assertEquals(operacao.idOperacao, operacaoListOrigem[0].idOperacao)
    }

    @Test
    fun `deve encontrar uma operacao ja criado pelo conta de destino e pelo tipo da operacao`() {

        var operacao = repositoryOperacao.findById(operacaoId)

        val operacaoListOrigem = repositoryOperacao.findAllByContaDestinoAndTipoOperacao(
            id = operacao!!.contaDestino.id,
            operacao = operacao.tipoOperacao.name
        )
        assertNotNull(operacaoListOrigem)
        assertEquals(operacao.idOperacao, operacaoListOrigem[0].idOperacao)
    }
}