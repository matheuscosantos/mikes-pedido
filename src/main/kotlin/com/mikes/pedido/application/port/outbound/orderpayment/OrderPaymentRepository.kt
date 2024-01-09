package com.mikes.pedido.application.port.outbound.orderpayment

import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.port.outbound.orderpayment.dto.OrderPaymentOutboundResponse

interface OrderPaymentRepository {
    fun save(orderPayment: OrderPayment): OrderPaymentOutboundResponse
    fun findByOrderId(orderId: OrderId): OrderPaymentOutboundResponse?
}
