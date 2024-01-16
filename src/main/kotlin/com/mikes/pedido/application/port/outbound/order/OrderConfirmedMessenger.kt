package com.mikes.pedido.application.port.outbound.order

import com.mikes.pedido.application.port.outbound.order.dto.OrderConfirmedMessage

interface OrderConfirmedMessenger {
    fun send(orderConfirmedMessage: OrderConfirmedMessage)
}
