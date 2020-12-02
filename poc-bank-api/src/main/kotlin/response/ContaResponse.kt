package com.example.api.response

import java.time.LocalDateTime

data class ContaResponse(
    val id: String,
    val saldo: Double,
    val dataHora: LocalDateTime
)