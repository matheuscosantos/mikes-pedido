package com.mikes.pedido.application.core.usecase.customer

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.mapper.customer.CustomerDomainMapper
import com.mikes.pedido.application.port.outbound.customer.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class CreateCustomerUseCaseTest {
    @Test
    fun `when creating a customer with success, expect creation`() {
        val cpf = "92979654078"
        val exists = false
        val expectedCustomer = mockCustomer(cpf)

        val customerRepository = mockCustomerRepository(exists)
        val customerDomainMapper = mockCustomerDomainMapper(expectedCustomer, cpf)

        val createCustomerUseCase = CreateCustomerUseCase(customerRepository, customerDomainMapper)

        val customer =
            createCustomerUseCase.create(mockk())
                .getOrThrow()

        Assertions.assertEquals(expectedCustomer, customer)
    }

    private fun mockCustomerRepository(exists: Boolean) =
        mockk<CustomerRepository>().also {
            every { it.exists(any()) } returns exists
            every { it.save(any()) } returns mockk()
        }

    private fun mockCustomerDomainMapper(
        customer: Customer,
        cpf: String,
    ) = mockk<CustomerDomainMapper>().also {
        every { it.new(any()) } returns success(customer)
        every { it.new(any(), any(), any(), any()) } returns success(mockCustomer(cpf))
    }

    private fun mockCustomer(cpfValue: String) =
        mockk<Customer>().also {
            every { it.cpf } returns Cpf.new(cpfValue).getOrThrow()
        }
}
