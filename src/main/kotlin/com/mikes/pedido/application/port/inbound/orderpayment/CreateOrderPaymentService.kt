package com.mikes.pedido.application.port.inbound.orderpayment

import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId

fun interface CreateOrderPaymentService {
    fun execute(orderId: OrderId): Result<OrderPayment>
}
