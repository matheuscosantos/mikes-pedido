package com.mikes.pedido.application.port.inbound.customer.dto

data class CreateCustomerInboundRequest(
    val cpf: String,
    val name: String,
    val email: String
)
