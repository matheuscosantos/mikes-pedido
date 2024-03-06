package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.exception.order.InvalidOrderStatusException
import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.OrderItem
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemQuantity
import com.mikes.pedido.application.core.domain.order.valueobject.OrderNumber
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.mapper.order.OrderDomainMapper
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.inbound.order.CreateOrderService
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderInboundRequest
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderItemInboundRequest
import com.mikes.pedido.application.port.inbound.product.FindProductService
import com.mikes.pedido.application.port.outbound.order.OrderReceivedMessenger
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import com.mikes.pedido.application.port.outbound.order.dto.OrderReceivedMessage
import com.mikes.pedido.util.flatMap
import com.mikes.pedido.util.mapFailure
import java.time.LocalDateTime
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
    private val orderDomainMapper: OrderDomainMapper,
    private val findCustomerService: FindCustomerService,
    private val findProductService: FindProductService,
    private val orderReceivedMessenger: OrderReceivedMessenger,
) : CreateOrderService {
    override fun create(createOrderInboundRequest: CreateOrderInboundRequest): Result<Order> {
        return createOrder(createOrderInboundRequest)
            .flatMap { saveOrder(it) }
            .onSuccess { notifyOrderReceived(it) }
    }

    private fun createOrder(createOrderInboundRequest: CreateOrderInboundRequest): Result<Order> {
        val nullableCustomerId =
            findNullableCustomerId(createOrderInboundRequest.customerId)
                .getOrElse { return failure(it) }

        val orderItems =
            createOrderItems(createOrderInboundRequest.items)
                .getOrElse { return failure(it) }

        val orderNumberValue =
            findOrderNumber()
                .getOrElse { return failure(it) }

        val now = LocalDateTime.now()

        return orderDomainMapper.new(
            OrderId.generate(),
            orderNumberValue,
            nullableCustomerId,
            orderItems,
            OrderStatus.RECEIVED,
            now,
            now,
        )
    }

    private fun findNullableCustomerId(nullableCustomerIdValue: String?): Result<CustomerId?> {
        val customerIdValue =
            nullableCustomerIdValue
                ?: return success(null)

        val customer =
            findCustomerService.find(customerIdValue)
                .getOrElse { return failure(it) }

        return success(customer.id)
    }

    private fun createOrderItems(createOrderItemsInboundRequest: List<CreateOrderItemInboundRequest>): Result<List<OrderItem>> {
        val orderItems =
            createOrderItemsInboundRequest.map {
                createOrderItem(it).getOrElse { e -> return failure(e) }
            }

        return success(orderItems)
    }

    private fun createOrderItem(item: CreateOrderItemInboundRequest): Result<OrderItem> {
        val id = OrderItemId.generate()
        val product = findProductService.find(item.productId, active = true).getOrElse { return failure(it) }
        val quantity = OrderItemQuantity.new(item.quantity).getOrElse { return failure(it) }

        return orderDomainMapper.new(id, product, quantity)
    }

    private fun findOrderNumber(): Result<OrderNumber> {
        val orderNumberValue = orderRepository.findNumber()

        return OrderNumber.new(orderNumberValue)
    }

    private fun saveOrder(order: Order): Result<Order> {
        return orderRepository.save(order)
            .toOrder()
            .mapFailure { InvalidOrderStatusException("Order in invalid state.") }
    }

    private fun OrderOutboundResponse.toOrder(): Result<Order> {
        return orderDomainMapper.new(this)
    }

    private fun notifyOrderReceived(order: Order) {
        val message =
            OrderReceivedMessage(
                order.id.value,
                order.price.value,
            )

        orderReceivedMessenger.send(message)
    }
}
