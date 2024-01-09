package com.mikes.pedido.adapter.inbound.controller.order.dto

import com.mikes.pedido.application.port.inbound.order.dto.UpdateOrderStatusInboundRequest

data class UpdateOrderStatusRequest(
    val status: String,
) {
    fun toInbound(): UpdateOrderStatusInboundRequest {
        return UpdateOrderStatusInboundRequest(status)
    }
}
