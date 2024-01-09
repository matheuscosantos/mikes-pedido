package com.mikes.pedido.application.core.usecase.customer

import br.com.fiap.mikes.application.core.usecase.exception.customer.CustomerAlreadyExistsException
import br.com.fiap.mikes.util.mapFailure
import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.usecase.exception.customer.InvalidCustomerStateException
import com.mikes.pedido.application.mapper.customer.CustomerDomainMapper
import com.mikes.pedido.application.port.inbound.customer.CreateCustomerService
import com.mikes.pedido.application.port.inbound.customer.dto.CreateCustomerInboundRequest
import com.mikes.pedido.application.port.outbound.customer.CustomerRepository
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
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

        if (customerRepository.exists(customer.cpf)) {
            return failure(CustomerAlreadyExistsException("Cpf already exists: '${customer.cpf.value}'."))
        }

        return customerRepository.save(customer)
            .toCustomer()
            .mapFailure { (InvalidCustomerStateException("Customer in invalid state.")) }
    }

    private fun CreateCustomerInboundRequest.newCustomer(): Result<Customer> {
        val active = true
        val now = LocalDateTime.now()
        return customerDomainMapper.new(this, active, now, now)
    }

    private fun CustomerOutboundResponse.toCustomer(): Result<Customer> {
        return customerDomainMapper.new(this)
    }
}
