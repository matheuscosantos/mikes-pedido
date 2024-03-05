package com.mikes.pedido.application.port.outbound.customer.dto

import java.time.LocalDateTime

data class CustomerOutboundResponse(
    val id: String,
    val cpf: String,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
