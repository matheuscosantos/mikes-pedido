package com.mikes.pedido.adapter.outbound.messenger.sns

import com.mikes.pedido.adapter.outbound.messenger.sns.client.SnsMessenger
import com.mikes.pedido.application.port.outbound.order.OrderConfirmedMessenger
import com.mikes.pedido.application.port.outbound.order.dto.OrderConfirmedMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SnsOrderConfirmedMessenger(
    private val snsMessenger: SnsMessenger,
    @Value("\${sns.orderConfirmed.arn}")
    private val orderConfirmedArn: String,
) : OrderConfirmedMessenger {
    override fun send(orderConfirmedMessage: OrderConfirmedMessage) {
        snsMessenger.send(orderConfirmedArn, orderConfirmedMessage)
    }
}
