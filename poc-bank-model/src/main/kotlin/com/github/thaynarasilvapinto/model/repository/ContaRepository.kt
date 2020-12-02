package com.example.model.repository

import com.example.model.Conta
import java.util.*

interface ContaRepository {
    fun save(conta: Conta): Int

    fun findById(contaId: String): Conta?

    fun update(conta: Conta): Int

    fun deleteById(id: String): Int
}