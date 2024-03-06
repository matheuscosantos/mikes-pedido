package com.mikes.pedido.application.core.usecase.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.usecase.exception.customer.CustomerNotFoundException
import com.mikes.pedido.application.core.usecase.exception.customer.InvalidCustomerStateException
import com.mikes.pedido.application.mapper.customer.CustomerDomainMapper
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.outbound.customer.CustomerRepository
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
import com.mikes.pedido.util.mapFailure
import kotlin.Result.Companion.failure

class FindCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val customerDomainMapper: CustomerDomainMapper,
) : FindCustomerService {
    override fun find(customerIdValue: String): Result<Customer> {
        val customerId =
            CustomerId.new(customerIdValue)
                .getOrElse { return failure(it) }

        val customerOutboundResponse =
            customerRepository.find(customerId, active = true)
                ?: return failure(CustomerNotFoundException("CustomerId='${customerId.value}' not found."))

        return customerOutboundResponse
            .toCustomer()
            .mapFailure { (InvalidCustomerStateException("Customer in invalid state.")) }
    }

    private fun CustomerOutboundResponse.toCustomer(): Result<Customer> {
        return customerDomainMapper.new(this)
    }
}
