package com.example.model.repository

import com.example.model.Cliente
import java.util.*

interface ClienteRepository {
    fun save(cliente: Cliente): Int

    fun findById(clienteId: String): Cliente?

    fun update(cliente: Cliente): Int

    fun deleteById(id: String): Int

    fun findByCpfEquals(cpf: String): Cliente?
}