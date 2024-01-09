package com.mikes.pedido.application.port.inbound.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.port.inbound.customer.dto.CreateCustomerInboundRequest

fun interface CreateCustomerService {
    fun create(createCustomerInboundRequest: CreateCustomerInboundRequest): Result<Customer>
}
