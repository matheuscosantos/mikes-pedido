package com.mikes.pedido.application.port.outbound.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse

interface CustomerRepository {
    fun save(customer: Customer): CustomerOutboundResponse
    fun find(cpf: Cpf, active: Boolean): CustomerOutboundResponse?
    fun exists(cpf: Cpf): Boolean
}
