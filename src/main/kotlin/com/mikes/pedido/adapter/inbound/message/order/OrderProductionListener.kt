package com.mikes.pedido.adapter.inbound.message.order

import com.mikes.pedido.adapter.inbound.message.order.dto.OrderProductionMessage
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class OrderProductionListener {
    @SqsListener("\${sqs.orderProduction.url}")
    fun listener(message: OrderProductionMessage) {
        Logger
            .getLogger("ProductionListener")
            .info("${message.orderId} : ${message.status}")
    }
}
