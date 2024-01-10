package com.mikes.pedido.application.core.usecase.order

import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemId
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.exception.order.InvalidOrderStatusException
import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.OrderItem
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemQuantity
import com.mikes.pedido.application.core.domain.order.valueobject.OrderNumber
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.mapper.order.OrderDomainMapper
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.inbound.order.CreateOrderService
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderInboundRequest
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderItemInboundRequest
import com.mikes.pedido.application.port.inbound.product.FindProductService
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
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
) : CreateOrderService {
    override fun create(createOrderInboundRequest: CreateOrderInboundRequest): Result<Order> {
        return createOrder(createOrderInboundRequest)
            .flatMap { saveOrder(it) }
        // .onSuccess { createOrderPaymentService.execute(it.id) } // todo posta mensagerm
    }

    private fun createOrder(createOrderInboundRequest: CreateOrderInboundRequest): Result<Order> {
        val nullableCpf =
            findNullableCpf(createOrderInboundRequest.cpf)
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
            nullableCpf,
            orderItems,
            OrderStatus.RECEIVED,
            now,
            now,
        )
    }

    private fun findNullableCpf(nullableCpf: String?): Result<Cpf?> {
        val cpf =
            nullableCpf
                ?: return success(null)

        val customer =
            findCustomerService.find(cpf, active = true)
                .getOrElse { return failure(it) }

        return success(customer.cpf)
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
}
