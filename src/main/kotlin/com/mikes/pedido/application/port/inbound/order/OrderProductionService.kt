package com.mikes.pedido.application.port.inbound.order

import com.mikes.pedido.application.port.inbound.order.dto.OrderProductionInboundRequest

fun interface OrderProductionService {
    fun process(orderProductionInboundRequest: OrderProductionInboundRequest): Result<Unit>
}
