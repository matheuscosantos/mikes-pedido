package com.mikes.pedido.adapter.inbound.message.order

import com.mikes.pedido.adapter.inbound.message.order.dto.OrderPaymentMessage
import com.mikes.pedido.application.port.inbound.order.OrderPaymentService
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class OrderPaymentListener(
    private val orderPaymentService: OrderPaymentService,
) {
    @SqsListener("\${sqs.orderPayment.url}")
    @CacheEvict("findOrdersWithDescriptions")
    fun listener(message: OrderPaymentMessage) {
        logger.info("received message with orderId '${message.orderId}' and status '${message.status}'.")

        orderPaymentService.process(message.toInbound())
            .onSuccess { logger.info("success processing message with orderId '${message.orderId}' and status '${message.status}'..") }
            .onFailure { logger.warning("error processing message: ${it.message}.") }
            .getOrThrow()
    }

    companion object {
        private val logger = Logger.getLogger("OrderPaymentListener")
    }
}
