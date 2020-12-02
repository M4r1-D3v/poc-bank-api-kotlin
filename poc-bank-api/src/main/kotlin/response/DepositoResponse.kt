package com.example.api.response

import java.time.LocalDateTime

data class DepositoResponse(
        val idOperacao: String,
        val valorOperacao: Double,
        val dataHora: LocalDateTime,
        val tipoOperacao: TipoOperacao
)