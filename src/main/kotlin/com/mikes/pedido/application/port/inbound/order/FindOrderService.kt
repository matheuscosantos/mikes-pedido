package com.mikes.pedido.application.port.inbound.order

import com.mikes.pedido.application.core.domain.order.Order

interface FindOrderService {
    fun findOrdersWithDescriptions(): Result<List<Order>>
}
