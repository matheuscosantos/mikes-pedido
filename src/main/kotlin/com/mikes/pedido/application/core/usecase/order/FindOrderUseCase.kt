package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.usecase.exception.order.InvalidOrderStateException
import com.mikes.pedido.application.core.usecase.exception.order.OrderNotFoundException
import com.mikes.pedido.application.mapper.order.OrderDomainMapper
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import com.mikes.pedido.util.mapFailure
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@CacheConfig(cacheNames = ["mikes-redis-cluster"])
open class FindOrderUseCase(
    private val orderRepository: OrderRepository,
    private val orderDomainMapper: OrderDomainMapper,
) : FindOrderService {
    @Cacheable("findOrdersWithDescriptions")
    override fun findOrdersWithDescriptions(): Result<List<Order>> {
        val orders = orderRepository.findOrdersWithDescriptions().map { it.toOrder().getOrElse { e -> return failure(e) } }
        return success(orders)
    }

    override fun find(orderId: OrderId): Result<Order> {
        val orderOutboundResponse =
            orderRepository.findById(orderId)
                ?: return failure(OrderNotFoundException("OrderId='${orderId.value}' not found."))

        return orderOutboundResponse
            .toOrder()
            .mapFailure { InvalidOrderStateException("OrderId='${orderId.value}' in invalid state.") }
    }

    private fun OrderOutboundResponse.toOrder(): Result<Order> {
        return orderDomainMapper.new(this)
    }
}
