package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderPaymentStatus
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.inbound.order.OrderPaymentService
import com.mikes.pedido.application.port.inbound.order.dto.OrderPaymentInboundRequest
import com.mikes.pedido.application.port.outbound.order.OrderConfirmedMessenger
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderConfirmedMessage
import com.mikes.pedido.util.flatMap
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class OrderPaymentUserCase(
    private val orderConfirmedMessenger: OrderConfirmedMessenger,
    private val findOrderService: FindOrderService,
    private val orderRepository: OrderRepository,
) : OrderPaymentService {
    override fun process(orderPaymentInboundRequest: OrderPaymentInboundRequest): Result<Unit> {
        val orderPaymentStatus =
            OrderPaymentStatus.findByValue(orderPaymentInboundRequest.status)
                .getOrElse { return failure(it) }

        val order =
            OrderId.new(orderPaymentInboundRequest.orderId)
                .flatMap { findOrderService.find(it) }
                .getOrElse { return failure(it) }

        return when (orderPaymentStatus) {
            OrderPaymentStatus.WAITING -> success(Unit)
            OrderPaymentStatus.REFUSED -> processRefused(order)
            OrderPaymentStatus.ACCEPTED -> processAccepted(order)
        }
    }

    private fun processRefused(order: Order): Result<Unit> {
        if (!isNewOrder(order)) {
            return success(Unit)
        }

        updateStatus(order, OrderStatus.CANCELLED)

        return success(Unit)
    }

    private fun processAccepted(order: Order): Result<Unit> {
        if (!isNewOrder(order)) {
            return success(Unit)
        }

        updateStatus(order, OrderStatus.PAID)

        notifyOrderConfirmed(order)

        return success(Unit)
    }

    private fun isNewOrder(order: Order): Boolean {
        return order.orderStatus == OrderStatus.RECEIVED
    }

    private fun updateStatus(
        order: Order,
        orderStatus: OrderStatus,
    ) {
        val updatedOrder = order.updateStatus(orderStatus)
        orderRepository.save(updatedOrder)
    }

    private fun notifyOrderConfirmed(order: Order) {
        val message = OrderConfirmedMessage(order.id.value)
        orderConfirmedMessenger.send(message)
    }
}
