package com.mikes.pedido.adapter.outbound.messenger.sns

import com.mikes.pedido.adapter.outbound.messenger.sns.client.SnsMessenger
import com.mikes.pedido.application.port.outbound.order.OrderReceivedMessenger
import com.mikes.pedido.application.port.outbound.order.dto.OrderReceivedMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SnsOrderReceivedMessenger(
    private val snsMessenger: SnsMessenger,
    @Value("\${sns.orderReceived.topic}")
    private val orderReceivedTopic: String,
) : OrderReceivedMessenger {
    override fun send(orderReceivedMessage: OrderReceivedMessage) {
        snsMessenger.send(orderReceivedTopic, orderReceivedMessage)
    }
}
