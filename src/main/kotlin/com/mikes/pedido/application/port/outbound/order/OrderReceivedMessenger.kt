package com.mikes.pedido.application.port.outbound.order

import com.mikes.pedido.application.port.outbound.order.dto.OrderReceivedMessage

interface OrderReceivedMessenger {
    fun send(orderReceivedMessage: OrderReceivedMessage)
}
