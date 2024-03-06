package com.mikes.pedido.adapter.outbound.database.entity

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "pedido")
data class OrderEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String,
    @Column(name = "numero")
    val number: Long,
    @Column(name = "cliente_id", length = 36)
    val customerId: String?,
    @Column(name = "preco")
    val price: BigDecimal,
    @Column(name = "status", length = 50)
    val status: String,
    @Column(name = "criado_em")
    val createdAt: LocalDateTime,
    @Column(name = "atualizado_em")
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(order: Order): OrderEntity =
            with(order) {
                return OrderEntity(
                    id.value,
                    number.value,
                    customerId?.value,
                    price.value,
                    orderStatus.name,
                    createdAt,
                    updatedAt,
                )
            }
    }

    fun toOutbound(orderItemsEntity: List<OrderItemEntity>): OrderOutboundResponse {
        val items = orderItemsEntity.map { it.toOutbound() }
        return OrderOutboundResponse(
            id,
            number,
            customerId,
            items,
            price,
            status,
            createdAt,
            updatedAt,
        )
    }
}
