package com.mikes.pedido.application.port.outbound.order

import com.mikes.pedido.application.port.outbound.order.dto.OrderConfirmedMessage

fun interface OrderConfirmedMessenger {
    fun send(orderConfirmedMessage: OrderConfirmedMessage)
}
