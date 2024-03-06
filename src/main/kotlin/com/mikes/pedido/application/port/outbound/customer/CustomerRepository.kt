package com.mikes.pedido.application.port.outbound.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse

interface CustomerRepository {
    fun save(customer: Customer): CustomerOutboundResponse

    fun find(
        customerId: CustomerId,
        active: Boolean,
    ): CustomerOutboundResponse?
}
