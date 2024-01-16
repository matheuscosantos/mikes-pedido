package com.mikes.pedido.adapter.inbound.message.order

import com.mikes.pedido.adapter.inbound.message.order.dto.OrderPaymentMessage
import com.mikes.pedido.application.port.inbound.order.OrderPaymentService
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class OrderPaymentListener(
    private val orderPaymentService: OrderPaymentService,
) {
    @SqsListener("\${sqs.orderPayment.url}")
    fun listener(message: OrderPaymentMessage) {
        orderPaymentService.process(message.toInbound())
    }
}
