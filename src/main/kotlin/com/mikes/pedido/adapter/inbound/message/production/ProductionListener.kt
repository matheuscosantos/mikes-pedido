package com.mikes.pedido.adapter.inbound.message.production

import com.mikes.pedido.adapter.inbound.message.production.dto.ProductionMessage
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class ProductionListener {
    @SqsListener("\${sqs.orderProduction.url}")
    fun listener(message: ProductionMessage) {
        Logger
            .getLogger("ProductionListener")
            .info("${message.orderId} : ${message.status}")
    }
}
