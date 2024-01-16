package com.mikes.pedido.application.port.inbound.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId

interface FindOrderService {
    fun findOrdersWithDescriptions(): Result<List<Order>>

    fun find(orderId: OrderId): Result<Order>
}
