package com.mikes.pedido.adapter.outbound.database

import com.mikes.pedido.adapter.outbound.database.entity.OrderEntity
import com.mikes.pedido.adapter.outbound.database.entity.OrderItemEntity
import com.mikes.pedido.adapter.outbound.database.jpa.OrderItemJpaRepository
import com.mikes.pedido.adapter.outbound.database.jpa.OrderJpaRepository
import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import kotlin.jvm.optionals.getOrNull

open class OrderDatabaseRepository(
    private val orderJpaRepository: OrderJpaRepository,
    private val orderItemJpaRepository: OrderItemJpaRepository,
) : OrderRepository {

    override fun findNumber(): Long {
        return orderJpaRepository.findNumber()
    }

    override fun save(order: Order): OrderOutboundResponse {
        val orderEntity = orderJpaRepository.save(OrderEntity.from(order))

        val orderItemsEntity = order.items
            .map { OrderItemEntity.from(order.id, it) }
            .map { orderItemJpaRepository.save(it) }

        return orderEntity.toOutbound(orderItemsEntity)
    }

    override fun findOrdersWithDescriptions(): List<OrderOutboundResponse> {
        return orderJpaRepository.findOrdersWithDescriptions().map {
            it.toOutbound(orderItemJpaRepository.findByOrderId(it.id))
        }
    }

    override fun findById(orderId: OrderId): OrderOutboundResponse? {
        return orderJpaRepository.findById(orderId.value)
            .map { it.toOutbound(orderItemJpaRepository.findByOrderId(it.id)) }
            .getOrNull()
    }
}
