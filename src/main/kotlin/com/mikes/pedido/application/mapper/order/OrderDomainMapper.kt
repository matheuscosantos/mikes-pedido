package com.mikes.pedido.application.mapper.order

import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemId
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.OrderItem
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemQuantity
import com.mikes.pedido.application.core.domain.order.valueobject.OrderNumber
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.port.outbound.order.dto.OrderItemOutboundResponse
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import java.time.LocalDateTime

interface OrderDomainMapper {
    fun new(
        id: OrderId,
        orderNumber: OrderNumber,
        nullableCpf: Cpf?,
        items: List<OrderItem>,
        orderStatus: OrderStatus,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<Order>

    fun new(
        id: OrderItemId,
        product: Product,
        quantity: OrderItemQuantity,
    ): Result<OrderItem>

    fun new(orderOutboundResponse: OrderOutboundResponse): Result<Order>

    fun new(orderItemOutboundResponse: OrderItemOutboundResponse): Result<OrderItem>
}
