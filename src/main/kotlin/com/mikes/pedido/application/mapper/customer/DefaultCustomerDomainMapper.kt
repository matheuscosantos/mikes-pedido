package com.mikes.pedido.application.mapper.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.customer.valueobject.Email
import com.mikes.pedido.application.core.domain.customer.valueobject.PersonName
import com.mikes.pedido.application.port.inbound.customer.dto.CreateCustomerInboundRequest
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
import java.time.LocalDateTime
import kotlin.Result.Companion.failure

class DefaultCustomerDomainMapper : CustomerDomainMapper {
    override fun new(
        createCustomerInboundRequest: CreateCustomerInboundRequest,
        customerId: CustomerId,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<Customer> =
        with(createCustomerInboundRequest) {
            val cpf = Cpf.new(cpf).getOrElse { return failure(it) }
            val name = PersonName.new(name).getOrElse { return failure(it) }
            val email = Email.new(email).getOrElse { return failure(it) }

            return Customer.new(customerId, cpf, name, email, createdAt, updatedAt)
        }

    override fun new(customerOutboundResponse: CustomerOutboundResponse): Result<Customer> =
        with(customerOutboundResponse) {
            val id = CustomerId.new(id).getOrElse { return failure(it) }
            val cpf = Cpf.new(cpf).getOrElse { return failure(it) }
            val name = PersonName.new(name).getOrElse { return failure(it) }
            val email = Email.new(email).getOrElse { return failure(it) }

            return Customer.new(id, cpf, name, email, createdAt, updatedAt)
        }
}
