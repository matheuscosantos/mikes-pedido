package com.mikes.pedido.infrastructure.instantiation

import com.mikes.pedido.adapter.outbound.database.OrderDatabaseRepository
import com.mikes.pedido.adapter.outbound.database.jpa.OrderItemJpaRepository
import com.mikes.pedido.adapter.outbound.database.jpa.OrderJpaRepository
import com.mikes.pedido.application.core.usecase.order.CreateOrderUseCase
import com.mikes.pedido.application.core.usecase.order.FindOrderUseCase
import com.mikes.pedido.application.core.usecase.order.OrderPaymentUseCase
import com.mikes.pedido.application.core.usecase.order.OrderProductionUseCase
import com.mikes.pedido.application.mapper.order.DefaultOrderDomainMapper
import com.mikes.pedido.application.mapper.order.OrderDomainMapper
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.inbound.order.CreateOrderService
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.inbound.order.OrderPaymentService
import com.mikes.pedido.application.port.inbound.order.OrderProductionService
import com.mikes.pedido.application.port.inbound.product.FindProductService
import com.mikes.pedido.application.port.outbound.order.OrderConfirmedMessenger
import com.mikes.pedido.application.port.outbound.order.OrderReceivedMessenger
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderPortsInstantiationConfig {
    @Bean
    fun orderDomainMapper(): OrderDomainMapper {
        return DefaultOrderDomainMapper()
    }

    @Bean
    fun orderRepository(
        orderJpaRepository: OrderJpaRepository,
        orderItemJpaRepository: OrderItemJpaRepository,
    ): OrderRepository {
        return (
            @Transactional object : OrderDatabaseRepository(
                orderJpaRepository,
                orderItemJpaRepository,
            ) {}
            )
    }

    @Bean
    fun createOrderService(
        orderRepository: OrderRepository,
        orderDomainMapper: OrderDomainMapper,
        findCustomerService: FindCustomerService,
        findProductService: FindProductService,
        orderReceivedMessenger: OrderReceivedMessenger,
    ): CreateOrderService {
        return CreateOrderUseCase(
            orderRepository,
            orderDomainMapper,
            findCustomerService,
            findProductService,
            orderReceivedMessenger,
        )
    }

    @Bean
    fun findOrderService(
        orderRepository: OrderRepository,
        orderDomainMapper: OrderDomainMapper,
    ): FindOrderService {
        return FindOrderUseCase(orderRepository, orderDomainMapper)
    }

    @Bean
    fun orderPaymentService(
        orderConfirmedMessenger: OrderConfirmedMessenger,
        findOrderService: FindOrderService,
        orderRepository: OrderRepository,
    ): OrderPaymentService {
        return OrderPaymentUseCase(orderConfirmedMessenger, findOrderService, orderRepository)
    }

    @Bean
    fun orderProductionService(
        findOrderService: FindOrderService,
        orderRepository: OrderRepository,
    ): OrderProductionService {
        return OrderProductionUseCase(findOrderService, orderRepository)
    }
}
