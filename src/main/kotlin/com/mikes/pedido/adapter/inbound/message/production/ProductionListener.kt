package com.mikes.pedido.adapter.inbound.message.production

import com.mikes.pedido.adapter.inbound.message.production.dto.ProductionMessage
import io.awspring.cloud.sqs.annotation.SqsListener
import java.util.logging.Logger

class ProductionListener {
    @SqsListener("\${sqs.orderProduction.queue}")
    fun listener(message: ProductionMessage) {
        Logger
            .getLogger("ProductionListener")
            .info("${message.orderId} : ${message.status}")
    }
}
