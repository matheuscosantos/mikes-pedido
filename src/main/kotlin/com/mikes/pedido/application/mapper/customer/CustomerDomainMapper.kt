package com.mikes.pedido.application.mapper.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.port.inbound.customer.dto.CreateCustomerInboundRequest
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
import java.time.LocalDateTime

interface CustomerDomainMapper {
    fun new(
        createCustomerInboundRequest: CreateCustomerInboundRequest,
        customerId: CustomerId,
        active: Boolean,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<Customer>

    fun new(customerOutboundResponse: CustomerOutboundResponse): Result<Customer>
}
