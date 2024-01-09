package com.mikes.pedido.adapter.inbound.controller.customer.dto

import com.mikes.pedido.application.port.inbound.customer.dto.CreateCustomerInboundRequest

data class CreateCustomerRequest(
    val cpf: String,
    val name: String,
    val email: String,
) {
    fun toInbound(): CreateCustomerInboundRequest {
        return CreateCustomerInboundRequest(
            cpf,
            name,
            email,
        )
    }
}
