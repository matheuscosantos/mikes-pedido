package com.mikes.pedido.application.port.inbound.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.port.inbound.order.dto.UpdateOrderStatusInboundRequest

fun interface UpdateOrderStatusService {
    fun update(id: String, updateOrderStatusInboundRequest: UpdateOrderStatusInboundRequest): Result<Order>
}
