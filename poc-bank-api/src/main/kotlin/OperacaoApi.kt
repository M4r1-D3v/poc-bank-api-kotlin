package com.example.api

import com.example.api.request.OperacaoRequest
import com.example.api.response.DepositoResponse
import com.example.api.response.SaqueResponse
import com.example.api.response.TransferenciaResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
interface OperacaoApi {
    @PostMapping(value = ["/conta/{id}/saque"])
    fun saque(@PathVariable id: String, @Valid @RequestBody operacao: OperacaoRequest): ResponseEntity<SaqueResponse>

    @PostMapping(value = ["/conta/{id}/deposito"])
    fun deposito(@PathVariable id: String, @Valid @RequestBody operacao: OperacaoRequest): ResponseEntity<DepositoResponse>

    @PostMapping(value = ["/conta/{id}/transferencia"])
    fun transferencia(@PathVariable id: String, @Valid @RequestBody operacaoTransferenciaRequest: OperacaoRequest): ResponseEntity<TransferenciaResponse>
}