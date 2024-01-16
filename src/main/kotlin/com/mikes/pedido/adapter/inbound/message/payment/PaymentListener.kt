package com.mikes.pedido.adapter.inbound.message.payment

import com.mikes.pedido.adapter.inbound.message.payment.dto.PaymentMessage
import io.awspring.cloud.sqs.annotation.SqsListener
import java.util.logging.Logger

class PaymentListener {
    @SqsListener("\${sqs.orderPayment.url}")
    fun listener(message: PaymentMessage) {
        Logger
            .getLogger("PaymentListener")
            .info("${message.orderId} : ${message.status}")
    }
}
