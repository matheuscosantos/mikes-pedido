package com.mikes.pedido.application.core.usecase.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.usecase.exception.customer.InvalidCustomerStateException
import com.mikes.pedido.application.mapper.customer.CustomerDomainMapper
import com.mikes.pedido.application.port.inbound.customer.CreateCustomerService
import com.mikes.pedido.application.port.inbound.customer.dto.CreateCustomerInboundRequest
import com.mikes.pedido.application.port.outbound.customer.CustomerRepository
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
import com.mikes.pedido.util.mapFailure
import java.time.LocalDateTime
import kotlin.Result.Companion.failure

class CreateCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val customerDomainMapper: CustomerDomainMapper,
) : CreateCustomerService {
    override fun create(createCustomerInboundRequest: CreateCustomerInboundRequest): Result<Customer> {
        val customer =
            createCustomerInboundRequest
                .newCustomer()
                .getOrElse { return failure(it) }

        return customerRepository.save(customer)
            .toCustomer()
            .mapFailure { (InvalidCustomerStateException("Customer in invalid state.")) }
    }

    private fun CreateCustomerInboundRequest.newCustomer(): Result<Customer> {
        val active = true
        val now = LocalDateTime.now()
        return customerDomainMapper.new(this, CustomerId.generate(), active, now, now)
    }

    private fun CustomerOutboundResponse.toCustomer(): Result<Customer> {
        return customerDomainMapper.new(this)
    }
}
