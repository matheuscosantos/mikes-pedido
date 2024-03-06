package com.mikes.pedido.adapter.outbound.database

import com.mikes.pedido.adapter.outbound.database.entity.CustomerEntity
import com.mikes.pedido.adapter.outbound.database.jpa.CustomerJpaRepository
import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.customer.valueobject.Email
import com.mikes.pedido.application.core.domain.customer.valueobject.PersonName
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CustomerDatabaseRepositoryTest {
    @Test
    fun testSave() {
        val customerJpaRepository = mockk<CustomerJpaRepository>()
        val customerRepository = CustomerDatabaseRepository(customerJpaRepository)

        val customer =
            Customer.new(
                CustomerId.generate(),
                Cpf.new("16223596073").getOrThrow(),
                email = Email.new("teste@teste.com").getOrThrow(),
                createdAt = date,
                updatedAt = date,
                personName = PersonName.new("teste").getOrThrow(),
            ).getOrThrow()

        val customerEntity = CustomerEntity.from(customer)
        val savedCustomerEntity = customerEntity.copy(cpf = "16223596073") // Assuming an ID is generated after saving

        every { customerJpaRepository.save(any()) } returns savedCustomerEntity

        val result = customerRepository.save(customer)

        assertEquals(savedCustomerEntity.toOutbound(), result)
    }

    companion object {
        private val date = LocalDateTime.now()
    }
}
