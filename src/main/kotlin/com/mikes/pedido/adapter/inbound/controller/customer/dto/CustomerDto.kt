package com.mikes.pedido.adapter.inbound.controller.customer.dto

import com.mikes.pedido.application.core.domain.customer.Customer
import java.time.LocalDateTime

data class CustomerDto(
    val cpf: String,
    val name: String,
    val email: String,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(customer: Customer): CustomerDto =
            with(customer) {
                return CustomerDto(
                    cpf.value,
                    name.value,
                    email.value,
                    active,
                    createdAt,
                    updatedAt,
                )
            }
    }
}
