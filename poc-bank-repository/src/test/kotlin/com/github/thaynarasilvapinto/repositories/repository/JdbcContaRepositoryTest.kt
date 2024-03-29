package com.example.repositories.repository

import com.example.model.Conta
import com.example.model.repository.ContaRepository
import com.example.repositories.config.RepositoryBaseTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class JdbcContaRepositoryTest : RepositoryBaseTest() {
    @Autowired
    private lateinit var repositoryConta: ContaRepository

    private var accountId: String = "-1"

    @Before
    fun setup() {
        accountId = saveAAccount()
    }

    @After
    fun tearDown() {
        repositoryConta.deleteById(accountId)
    }

    private fun saveAAccount(): String {

        val account = Conta(
            saldo = 0.00
        )
        assertEquals(1, repositoryConta.save(account))
        return account.id
    }


    @Test
    fun `deve encontrar uma conta ja criada`() {
        val account = repositoryConta.findById(accountId)
        assertNotNull(account)
        assertEquals(accountId, account!!.id)
    }
    @Test
    fun `noa deve encontrar uma conta nao criada`() {
        val account = repositoryConta.findById("-1")
        assertNull(account)
    }

    @Test
    fun `deve criar uma conta`() {
        val account = Conta(
            saldo = 0.00
        )
        assertEquals(1, repositoryConta.save(account))

        repositoryConta.deleteById(account.id)
    }

    @Test
    fun `deve fazer um update em uma conta ja criada`() {
        var conta = repositoryConta.findById(accountId)
        var contaEsperado = conta
        contaEsperado!!.saldo = 10.00

        repositoryConta.update(contaEsperado)

        assertEquals(repositoryConta.findById(accountId)!!.saldo, contaEsperado.saldo)
    }

    @Test
    fun `deve deletar em uma conta ja criado`() {

        val account = Conta(
            saldo = 0.00
        )
        repositoryConta.save(account)

        assertEquals(1, repositoryConta.deleteById(account.id))

    }
}