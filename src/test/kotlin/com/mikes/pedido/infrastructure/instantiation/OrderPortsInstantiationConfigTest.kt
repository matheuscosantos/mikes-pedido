package com.mikes.pedido.infrastructure.instantiation

import com.mikes.pedido.adapter.outbound.database.OrderDatabaseRepository
import com.mikes.pedido.application.core.usecase.order.CreateOrderUseCase
import com.mikes.pedido.application.core.usecase.order.FindOrderUseCase
import com.mikes.pedido.application.core.usecase.order.OrderPaymentUseCase
import com.mikes.pedido.application.core.usecase.order.OrderProductionUseCase
import com.mikes.pedido.application.mapper.order.DefaultOrderDomainMapper
import io.mockk.mockk
import org.junit.jupiter.api.Test
import util.AssertionsUtil

internal class OrderPortsInstantiationConfigTest {
    @Test
    fun `when instantiating orderDomainMapper, expect DefaultOrderDomainMapper instance`() {
        val orderDomainMapper = OrderPortsInstantiationConfig().orderDomainMapper()

        AssertionsUtil.isInstanceType<DefaultOrderDomainMapper>(orderDomainMapper)
    }

    @Test
    fun `when instantiating orderRepository, expect OrderDatabaseRepository instance`() {
        val orderRepository = OrderPortsInstantiationConfig().orderRepository(mockk(), mockk())

        AssertionsUtil.isInstanceType<OrderDatabaseRepository>(orderRepository)
    }

    @Test
    fun `when instantiating createOrderService, expect CreateOrderUseCase instance`() {
        val createOrderService = OrderPortsInstantiationConfig().createOrderService(mockk(), mockk(), mockk(), mockk(), mockk())

        AssertionsUtil.isInstanceType<CreateOrderUseCase>(createOrderService)
    }

    @Test
    fun `when instantiating findOrderService, expect FindOrderUseCase instance`() {
        val findOrderService = OrderPortsInstantiationConfig().findOrderService(mockk(), mockk())

        AssertionsUtil.isInstanceType<FindOrderUseCase>(findOrderService)
    }

    @Test
    fun `when instantiating orderPaymentService, expect OrderPaymentUseCase instance`() {
        val orderPaymentService = OrderPortsInstantiationConfig().orderPaymentService(mockk(), mockk(), mockk())

        AssertionsUtil.isInstanceType<OrderPaymentUseCase>(orderPaymentService)
    }

    @Test
    fun `when instantiating orderProductionService, expect OrderProductionUseCase instance`() {
        val orderProductionService = OrderPortsInstantiationConfig().orderProductionService(mockk(), mockk())

        AssertionsUtil.isInstanceType<OrderProductionUseCase>(orderProductionService)
    }
}
