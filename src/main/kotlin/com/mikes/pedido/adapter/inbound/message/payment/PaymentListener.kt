package com.mikes.pedido.adapter.inbound.message.payment

import com.mikes.pedido.adapter.inbound.message.payment.dto.PaymentMessage
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class PaymentListener {
    @SqsListener("\${sqs.orderPayment.url}")
    fun listener(message: PaymentMessage) {
        Logger
            .getLogger("PaymentListener")
            .info("${message.orderId} : ${message.status}")
    }
}
