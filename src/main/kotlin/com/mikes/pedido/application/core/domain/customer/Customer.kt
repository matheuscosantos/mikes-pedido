package com.mikes.pedido.application.core.domain.customer

import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.customer.valueobject.Email
import com.mikes.pedido.application.core.domain.customer.valueobject.PersonName
import java.time.LocalDateTime
import kotlin.Result.Companion.success

class Customer private constructor(
    val id: CustomerId,
    val cpf: Cpf,
    val name: PersonName,
    val email: Email,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun new(
            id: CustomerId,
            cpf: Cpf,
            personName: PersonName,
            email: Email,
            active: Boolean,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
        ): Result<Customer> = success(Customer(id, cpf, personName, email, active, createdAt, updatedAt))
    }

    fun updatedToDeletion(): Customer {
        return Customer(
            id,
            Cpf.anonymous(),
            PersonName.anonymous(),
            Email.anonymous(),
            active = false,
            createdAt,
            LocalDateTime.now(),
        )
    }
}
