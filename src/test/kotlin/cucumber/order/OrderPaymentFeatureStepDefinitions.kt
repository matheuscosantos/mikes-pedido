package cucumber.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderPaymentStatus
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.core.usecase.order.OrderPaymentUseCase
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.inbound.order.OrderPaymentService
import com.mikes.pedido.application.port.inbound.order.dto.OrderPaymentInboundRequest
import com.mikes.pedido.application.port.outbound.order.OrderConfirmedMessenger
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

internal class OrderPaymentFeatureStepDefinitions {
    private lateinit var orderConfirmedMessenger: OrderConfirmedMessenger
    private lateinit var findOrderService: FindOrderService
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderPaymentService: OrderPaymentService
    private lateinit var order: Order

    @Given("Aplicacao esta processando um pagamento")
    fun `aplicacao esta processando um pagamento`() {
        orderConfirmedMessenger = mockk<OrderConfirmedMessenger>()
        findOrderService = mockk<FindOrderService>()
        orderRepository = mockk<OrderRepository>()

        orderPaymentService =
            OrderPaymentUseCase(
                orderConfirmedMessenger,
                findOrderService,
                orderRepository,
            )

        every { orderRepository.save(any()) } returns mockk()
        every { orderConfirmedMessenger.send(any()) } returns Unit
    }

    @When("Pedido aguardando pagamento recebe o callback de status {string}")
    fun `pedido aguardando pagamento recebe o status de {orderPaymentStatus}`(orderPaymentStatus: String) {
        val orderId = UUID.randomUUID().toString()
        order = mockOrder(orderId, OrderStatus.RECEIVED)
        every { findOrderService.find(any()) } returns success(order)

        orderPaymentService.process(OrderPaymentInboundRequest(order.id.value, orderPaymentStatus))
    }

    @When("Pedido que nao esta aguardando pagamento recebe o callback de status {string}")
    fun `pedido que nao esta aguardando pagamento recebe o callback de status {orderPaymentStatus}`(orderPaymentStatus: String) {
        val orderId = UUID.randomUUID().toString()
        order = mockOrder(orderId, OrderStatus.FINISHED)
        every { findOrderService.find(any()) } returns success(order)

        orderPaymentService.process(OrderPaymentInboundRequest(order.id.value, orderPaymentStatus))
    }

    @When("Pedido inexistente recebe callback")
    fun `pedido inexistente recebe callback`() {
        val orderId = UUID.randomUUID().toString()
        order = mockOrder(orderId, OrderStatus.FINISHED)
        every { findOrderService.find(any()) } returns failure(mockk())

        orderPaymentService.process(OrderPaymentInboundRequest(order.id.value, OrderPaymentStatus.ACCEPTED.name))
    }

    @Then("Atualiza pedido para status {string}")
    fun `atualiza pedido para status {orderStatus}`(orderStatus: String) {
        verify(exactly = 1) {
            order.updateStatus(eq(OrderStatus.findByValue(orderStatus).getOrThrow()))
        }

        verify(exactly = 1) { orderRepository.save(any()) }
    }

    @Then("Nao atualiza pedido")
    fun `nao atualiza pedido`() {
        verify(exactly = 0) {
            order.updateStatus(any())
        }

        verify(exactly = 0) { orderRepository.save(any()) }
    }

    @Then("Notifica confirmacao do pedido")
    fun `notifica confirmacao do pedido`() {
        verify(exactly = 1) { orderConfirmedMessenger.send(any()) }
    }

    @Then("Nao notifica confirmacao do pedido")
    fun `nao notifica confirmacao do pedido`() {
        verify(exactly = 0) { orderConfirmedMessenger.send(any()) }
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
