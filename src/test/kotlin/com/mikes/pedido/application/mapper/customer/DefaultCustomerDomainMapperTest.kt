package com.mikes.pedido.application.mapper.customer

import com.mikes.pedido.application.port.inbound.customer.dto.CreateCustomerInboundRequest
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime.now

internal class DefaultCustomerDomainMapperTest {
    @Test
    fun `when creating new customer with CreateCustomerInboundRequest, expect attributes equality`() {
        val cpf = "92979654078"
        val name = "name"
        val email = "email@email.com"
        val active = true
        val createdAt = now()
        val updatedAt = now()

        val customerDomainMapper = DefaultCustomerDomainMapper()

        val customer =
            customerDomainMapper.new(CreateCustomerInboundRequest(cpf, name, email), active, createdAt, updatedAt)
                .getOrThrow()

        with(customer) {
            Assertions.assertEquals(cpf, this.cpf.value)
            Assertions.assertEquals(name, this.name.value)
            Assertions.assertEquals(email, this.email.value)
            Assertions.assertEquals(active, this.active)
            Assertions.assertEquals(createdAt, this.createdAt)
            Assertions.assertEquals(updatedAt, this.updatedAt)
        }
    }

    @Test
    fun `when creating new customer with CustomerOutboundResponse, expect attributes equality`() {
        val cpf = "92979654078"
        val name = "name"
        val email = "email@email.com"
        val active = true
        val createdAt = now()
        val updatedAt = now()

        val customerDomainMapper = DefaultCustomerDomainMapper()

        val customer =
            customerDomainMapper.new(CustomerOutboundResponse(cpf, name, email, active, createdAt, updatedAt))
                .getOrThrow()

        with(customer) {
            Assertions.assertEquals(cpf, this.cpf.value)
            Assertions.assertEquals(name, this.name.value)
            Assertions.assertEquals(email, this.email.value)
            Assertions.assertEquals(active, this.active)
            Assertions.assertEquals(createdAt, this.createdAt)
            Assertions.assertEquals(updatedAt, this.updatedAt)
        }
    }
}
