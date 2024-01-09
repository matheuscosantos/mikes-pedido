package com.mikes.pedido.application.port.inbound.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderInboundRequest

fun interface CreateOrderService {
    fun create(createOrderInboundRequest: CreateOrderInboundRequest): Result<Order>
}
