package com.example.api.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class OperacaoRequest(
    @get: NotNull
    @get: Min(1)
    val valorOperacao: Double?,
    val contaDestino: String?
)