package com.mikes.pedido.application.port.outbound.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse

interface OrderRepository {
    fun findNumber(): Long
    fun save(order: Order): OrderOutboundResponse
    fun findOrdersWithDescriptions(): List<OrderOutboundResponse>
    fun findById(orderId: OrderId): OrderOutboundResponse?
}
