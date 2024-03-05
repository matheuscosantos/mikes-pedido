package com.mikes.pedido.adapter.inbound.controller.customer

import com.mikes.pedido.adapter.inbound.controller.customer.dto.CreateCustomerRequest
import com.mikes.pedido.adapter.inbound.controller.customer.dto.CustomerDto
import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.customer.valueobject.Email
import com.mikes.pedido.application.core.domain.customer.valueobject.PersonName
import com.mikes.pedido.application.port.inbound.customer.CreateCustomerService
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class CustomerControllerTest {
    @MockK
    private lateinit var createCustomerService: CreateCustomerService

    @MockK
    private lateinit var findCustomerService: FindCustomerService

    @InjectMockKs
    private lateinit var customerController: CustomerController

    @Test
    fun `test create customer`() {
        val createCustomerRequest =
            CreateCustomerRequest(
                name = "teste",
                email = "teste@teste.com",
                cpf = "16223596073",
            )

        val customerId = CustomerId.generate()

        val customer =
            Customer.new(
                customerId,
                Cpf.new("16223596073").getOrThrow(),
                email = Email.new("teste@teste.com").getOrThrow(),
                createdAt = date,
                updatedAt = date,
                personName = PersonName.new("teste").getOrThrow(),
            )

        val customerDto =
            CustomerDto(
                customerId.value,
                "16223596073",
                email = "teste@teste.com",
                createdAt = date,
                updatedAt = date,
                name = "teste",
            )

        every { createCustomerService.create(any()) } returns customer

        val response = customerController.create(createCustomerRequest)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(customerDto, response.body)
    }

    @Test
    fun `test find customer`() {
        val customerId = CustomerId.generate()

        val customer =
            Customer.new(
                customerId,
                Cpf.new("16223596073").getOrThrow(),
                email = Email.new("teste@teste.com").getOrThrow(),
                createdAt = date,
                updatedAt = date,
                personName = PersonName.new("teste").getOrThrow(),
            )

        val customerDto =
            CustomerDto(
                customerId.value,
                "16223596073",
                email = "teste@teste.com",
                createdAt = date,
                updatedAt = date,
                name = "teste",
            )
        every { findCustomerService.find("123.456.789-00", true) } returns customer
        val response = customerController.find("123.456.789-00", true)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(customerDto, response.body)
    }

    companion object {
        private val date = LocalDateTime.now()
    }
}
