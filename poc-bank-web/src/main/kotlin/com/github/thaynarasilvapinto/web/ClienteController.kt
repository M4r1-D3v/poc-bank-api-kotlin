package com.example.web

import com.example.api.ClienteApi
import com.example.api.request.ClienteCriarRequest
import com.example.api.response.ClienteResponse
import com.example.model.Cliente
import com.example.model.Conta
import com.example.service.ClienteService
import com.example.web.utils.toResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
open class ClienteController @Autowired constructor(
    private val serviceCliente: ClienteService
) : ClienteApi {

    override fun mostrarCliente(@PathVariable("id") id: String): ResponseEntity<ClienteResponse> {
        val cliente = serviceCliente.cliente(id)
        return ResponseEntity.ok().body(cliente!!.toResponse())

    }

    override fun criarCliente(@Valid @RequestBody clienteCriarRequest: ClienteCriarRequest): ResponseEntity<ClienteResponse> {
        val cliente = serviceCliente.criarCliente(
            Cliente(
                nome = clienteCriarRequest.nome!!,
                cpf = clienteCriarRequest.cpf!!,
                conta = Conta(saldo = 0.00)
            )
        )
        return ResponseEntity.ok().body(cliente!!.toResponse())

    }
}