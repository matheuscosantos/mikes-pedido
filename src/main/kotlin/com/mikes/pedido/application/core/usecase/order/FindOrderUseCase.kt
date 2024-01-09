package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.mapper.order.OrderDomainMapper
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class FindOrderUseCase(
    private val orderRepository: OrderRepository,
    private val orderDomainMapper: OrderDomainMapper,
) : FindOrderService {
    override fun findOrdersWithDescriptions(): Result<List<Order>> {
        val orders = orderRepository.findOrdersWithDescriptions().map { it.toOrder().getOrElse { e -> return failure(e) } }
        return success(orders)
    }

    private fun OrderOutboundResponse.toOrder(): Result<Order> {
        return orderDomainMapper.new(this)
    }
}
