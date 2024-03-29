package com.example.api.response

import java.time.LocalDateTime

data class SaqueResponse(
        val idOperacao: String,
        val valorOperacao: Double,
        val dataHora: LocalDateTime,
        val tipoOperacao: TipoOperacao
)