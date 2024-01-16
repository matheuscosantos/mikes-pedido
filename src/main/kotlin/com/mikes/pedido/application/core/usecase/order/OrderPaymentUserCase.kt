package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderPaymentStatus
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.inbound.order.OrderPaymentService
import com.mikes.pedido.application.port.inbound.order.dto.OrderPaymentInboundRequest
import com.mikes.pedido.application.port.outbound.order.OrderConfirmedMessenger
import com.mikes.pedido.application.port.outbound.order.dto.OrderConfirmedMessage
import com.mikes.pedido.util.flatMap
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class OrderPaymentUserCase(
    private val orderConfirmedMessenger: OrderConfirmedMessenger,
    private val findOrderService: FindOrderService,
) : OrderPaymentService {
    override fun process(orderPaymentInboundRequest: OrderPaymentInboundRequest): Result<Unit> {
        val orderPaymentStatus =
            OrderPaymentStatus.findByValue(orderPaymentInboundRequest.status)
                .getOrElse { return failure(it) }

        val orderStatus =
            OrderId.new(orderPaymentInboundRequest.orderId)
                .flatMap { findOrderService.find(it) }
                .map { it.orderStatus }
                .getOrElse { return failure(it) }

        if (acceptedPaymentForNewOrder(orderPaymentStatus, orderStatus)
        ) {
            val message = OrderConfirmedMessage(orderPaymentInboundRequest.orderId)
            orderConfirmedMessenger.send(message)
        }

        return success(Unit)
    }

    private fun acceptedPaymentForNewOrder(
        orderPaymentStatus: OrderPaymentStatus,
        orderStatus: OrderStatus,
    ) = orderPaymentStatus == OrderPaymentStatus.ACCEPTED &&
        orderStatus == OrderStatus.RECEIVED
}
