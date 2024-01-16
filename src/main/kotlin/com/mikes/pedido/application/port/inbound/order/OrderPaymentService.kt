package com.mikes.pedido.application.port.inbound.order

import com.mikes.pedido.application.port.inbound.order.dto.OrderPaymentInboundRequest

interface OrderPaymentService {
    fun process(orderPaymentInboundRequest: OrderPaymentInboundRequest): Result<Unit>
}
