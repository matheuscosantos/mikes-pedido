package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.inbound.order.OrderProductionService
import com.mikes.pedido.application.port.inbound.order.dto.OrderProductionInboundRequest
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.util.flatMap
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class OrderProductionUseCase(
    private val findOrderService: FindOrderService,
    private val orderRepository: OrderRepository,
) : OrderProductionService {
    override fun process(orderProductionInboundRequest: OrderProductionInboundRequest): Result<Unit> {
        val orderStatus =
            OrderStatus.findByValue(orderProductionInboundRequest.status)
                .getOrElse { return failure(it) }

        val order =
            OrderId.new(orderProductionInboundRequest.orderId)
                .flatMap { findOrderService.find(it) }
                .getOrElse { return failure(it) }

        if (!isInvalidUpdateStatus(order.orderStatus, orderStatus)) {
            updateStatus(order, orderStatus)
        }

        return success(Unit)
    }

    private fun isInvalidUpdateStatus(
        currentStatus: OrderStatus,
        newStatus: OrderStatus,
    ): Boolean {
        return isEndStatus(currentStatus) ||
            isInvalidStatus(newStatus)
    }

    private fun isEndStatus(orderStatus: OrderStatus): Boolean {
        return orderStatus == OrderStatus.FINISHED ||
            orderStatus == OrderStatus.CANCELLED
    }

    private fun isInvalidStatus(orderStatus: OrderStatus): Boolean {
        return orderStatus == OrderStatus.RECEIVED ||
            orderStatus == OrderStatus.PAID
    }

    private fun updateStatus(
        order: Order,
        orderStatus: OrderStatus,
    ) {
        val updatedOrder = order.updateStatus(orderStatus)
        orderRepository.save(updatedOrder)
    }
}
