package com.mikes.pedido.application.core.usecase.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.mapper.customer.CustomerDomainMapper
import com.mikes.pedido.application.port.outbound.customer.CustomerRepository
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class FindCustomerUseCaseTest {
    @Test
    fun `when customer has found, expect success`() {
        val customerId = CustomerId.generate()

        val customerOutboundResponse = mockk<CustomerOutboundResponse>()
        val expectedCustomer = mockk<Customer>()

        val customerRepository = mockCustomerRepository(customerOutboundResponse)
        val customerDomainMapper = mockCustomerDomainMapper(success(expectedCustomer))

        val findCustomerService =
            FindCustomerUseCase(
                customerRepository,
                customerDomainMapper,
            )

        val actualCustomer =
            findCustomerService.find(customerId.value)
                .getOrThrow()

        verify { customerRepository.find(any()) }
        verify { customerDomainMapper.new(eq(customerOutboundResponse)) }

        Assertions.assertEquals(expectedCustomer, actualCustomer)
    }

    private fun mockCustomerRepository(nullableCustomerOutboundResponse: CustomerOutboundResponse?) =
        mockk<CustomerRepository>().also {
            every { it.find(any<CustomerId>()) } returns nullableCustomerOutboundResponse
        }

    private fun mockCustomerDomainMapper(customerResult: Result<Customer>) =
        mockk<CustomerDomainMapper>().also {
            every { it.new(any()) } returns customerResult
        }
}
