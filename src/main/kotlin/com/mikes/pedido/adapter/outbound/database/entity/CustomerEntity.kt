package com.mikes.pedido.adapter.outbound.database.entity

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.port.outbound.customer.dto.CustomerOutboundResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "cliente")
data class CustomerEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String,
    @Column(name = "cpf", length = 11)
    val cpf: String,
    @Column(name = "nome", length = 255)
    val name: String,
    @Column(name = "email", length = 255)
    val email: String,
    @Column(name = "ativo")
    val active: Boolean,
    @Column(name = "criado_em")
    val createdAt: LocalDateTime,
    @Column(name = "atualizado_em")
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(customer: Customer): CustomerEntity =
            with(customer) {
                return CustomerEntity(id.value, cpf.value, name.value, email.value, active, createdAt, updatedAt)
            }
    }

    fun toOutbound(): CustomerOutboundResponse {
        return CustomerOutboundResponse(
            id,
            cpf,
            name,
            email,
            active,
            createdAt,
            updatedAt,
        )
    }
}
