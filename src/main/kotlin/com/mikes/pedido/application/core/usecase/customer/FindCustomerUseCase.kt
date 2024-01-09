package com.mikes.pedido.application.core.usecase.customer

import br.com.fiap.mikes.util.mapFailure
import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.usecase.exception.customer.CustomerNotFoundException
import com.mikes.pedido.application.core.usecase.exception.customer.InvalidCustomerStateException
import com.mikes.pedido.application.mapper.customer.CustomerDomainMapper
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.outbound.customer.CustomerRepository
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
import kotlin.Result.Companion.failure

class FindCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val customerDomainMapper: CustomerDomainMapper,
) : FindCustomerService {
    override fun find(
        cpfValue: String,
        active: Boolean,
    ): Result<Customer> {
        val cpf =
            Cpf.new(cpfValue)
                .getOrElse { return failure(it) }

        val customerOutboundResponse =
            customerRepository.find(cpf, active)
                ?: return failure(CustomerNotFoundException("Cpf='${cpf.value}' not found for active='$active'."))

        return customerOutboundResponse
            .toCustomer()
            .mapFailure { (InvalidCustomerStateException("Customer in invalid state.")) }
    }

    private fun CustomerOutboundResponse.toCustomer(): Result<Customer> {
        return customerDomainMapper.new(this)
    }
}
