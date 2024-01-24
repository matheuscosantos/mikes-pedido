package cucumber.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.core.usecase.order.OrderProductionUseCase
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.inbound.order.OrderProductionService
import com.mikes.pedido.application.port.inbound.order.dto.OrderProductionInboundRequest
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

internal class OrderProductionFeatureStepDefinitions {
    private lateinit var findOrderService: FindOrderService
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderProductionService: OrderProductionService
    private lateinit var order: Order

    @Given("Pedido em producao esta {string}")
    fun `pedido em producao esta {string}`(orderStatusValue: String) {
        findOrderService = mockk<FindOrderService>()
        orderRepository = mockk<OrderRepository>()

        orderProductionService =
            OrderProductionUseCase(
                findOrderService,
                orderRepository,
            )

        every { orderRepository.save(any()) } returns mockk()

        val orderId = UUID.randomUUID().toString()
        val orderStatus = OrderStatus.findByValue(orderStatusValue).getOrThrow()

        order = mockOrder(orderId, orderStatus)

        every { findOrderService.find(any()) } returns success(order)
    }

    @Given("Pedido em producao nao existe")
    fun `pedido em producao nao existe`() {
        findOrderService = mockk<FindOrderService>()
        orderRepository = mockk<OrderRepository>()

        orderProductionService =
            OrderProductionUseCase(
                findOrderService,
                orderRepository,
            )

        every { orderRepository.save(any()) } returns mockk()

        val orderId = UUID.randomUUID().toString()
        val orderStatus = OrderStatus.PAID

        order = mockOrder(orderId, orderStatus)

        every { findOrderService.find(any()) } returns failure(Exception())
    }

    @When("Producao informa que pedido foi para {string}")
    fun `producao informa que pedido foi para {string}`(orderStatusValue: String) {
        val orderProductionInboundRequest = OrderProductionInboundRequest(order.id.value, orderStatusValue)

        orderProductionService.process(orderProductionInboundRequest)
    }

    @Then("Atualiza pedido em producao para {string}")
    fun `atualiza pedido em producao para {string}`(orderStatusValue: String) {
        val orderStatus = OrderStatus.findByValue(orderStatusValue).getOrThrow()

        verify(exactly = 1) { order.updateStatus(match { it == orderStatus }) }

        verify(exactly = 1) { orderRepository.save(any()) }
    }

    @Then("Nao atualiza pedido em producao")
    fun `nao atualiza pedido em producao`() {
        verify(exactly = 0) { order.updateStatus(any()) }

        verify(exactly = 0) { orderRepository.save(any()) }
    }

    private fun mockOrder(
        orderId: String,
        orderStatus: OrderStatus,
    ): Order =
        mockk<Order>().also {
            every { it.id } returns OrderId.new(orderId).getOrThrow()
            every { it.orderStatus } returns orderStatus

            every { it.updateStatus(any()) } answers {
                val newOrderStatus = it.invocation.args[0] as OrderStatus
                mockOrder(orderId, newOrderStatus)
            }
        }
}
