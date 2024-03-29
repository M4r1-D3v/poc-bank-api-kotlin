package com.example.service

import com.example.model.Cliente
import com.example.model.Conta
import com.example.model.repository.ClienteRepository
import com.example.model.repository.ContaRepository
import com.example.service.exception.AccountIsValidException
import com.example.service.exception.CpfIsValidException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
open class ClienteService (
    private val repo: ClienteRepository,
    private val repoConta: ContaRepository
) {


    fun find(id: String): Cliente? {
        return repo.findById(id)
    }

    fun insert(cliente: Cliente): Cliente? {
        repo.save(cliente)
        return repo.findById(cliente.id)
    }

    fun update(cliente: Cliente): Cliente? {
        find(cliente.id)
        repo.update(cliente)
        return repo.findById(cliente.id)
    }

    fun delete(id: String) {
        find(id)
        repo.deleteById(id)
    }

    fun findCPF(CPF: String): Cliente? {
        return repo.findByCpfEquals(CPF)
    }

    fun criarCliente(cliente: Cliente): Cliente? {

        if (findCPF(cliente.cpf) != null) {
            throw CpfIsValidException(message = "O CPF já existe")
        } else {
            val conta = insertToConta(cliente.conta)
            val client = Cliente(id = cliente.id, nome = cliente.nome, cpf = cliente.cpf, conta = conta!!)
            val clienteInserido = insert(client)
            return clienteInserido
        }
    }

    fun cliente(id: String): Cliente? {
        val cliente = find(id)

        if (cliente != null) {
            return cliente
        }
        throw AccountIsValidException(message = "A conta deve ser valida")
    }

    fun insertToConta(conta: Conta): Conta? {
        repoConta.save(conta)
        return repoConta.findById(conta.id)
    }
}