package com.mikes.pedido.adapter.inbound.message.order

import com.mikes.pedido.adapter.inbound.message.order.dto.OrderProductionMessage
import com.mikes.pedido.application.port.inbound.order.OrderProductionService
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class OrderProductionListener(
    private val orderProductionService: OrderProductionService,
) {
    @SqsListener("\${sqs.orderProduction.url}")
    fun listener(message: OrderProductionMessage) {
        orderProductionService.process(message.toInbound())
            .getOrThrow()
    }
}
