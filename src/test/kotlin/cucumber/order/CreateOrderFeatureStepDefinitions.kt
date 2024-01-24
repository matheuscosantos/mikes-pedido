package cucumber.order

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderPrice
import com.mikes.pedido.application.core.usecase.order.CreateOrderUseCase
import com.mikes.pedido.application.mapper.order.OrderDomainMapper
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.inbound.order.CreateOrderService
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderInboundRequest
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderItemInboundRequest
import com.mikes.pedido.application.port.inbound.product.FindProductService
import com.mikes.pedido.application.port.outbound.order.OrderReceivedMessenger
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.util.UUID
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

internal class CreateOrderFeatureStepDefinitions {
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderDomainMapper: OrderDomainMapper
    private lateinit var findCustomerService: FindCustomerService
    private lateinit var findProductService: FindProductService
    private lateinit var orderReceivedMessenger: OrderReceivedMessenger
    private lateinit var createOrderService: CreateOrderService

    @Given("Cliente esta fazendo um pedido")
    fun `cliente esta fazendo um pedido`() {
        orderRepository = mockk<OrderRepository>()
        orderDomainMapper = mockk<OrderDomainMapper>()
        findCustomerService = mockk<FindCustomerService>()
        findProductService = mockk<FindProductService>()
        orderReceivedMessenger = mockk<OrderReceivedMessenger>()

        createOrderService =
            CreateOrderUseCase(
                orderRepository,
                orderDomainMapper,
                findCustomerService,
                findProductService,
                orderReceivedMessenger,
            )

        every { orderRepository.findNumber() } returns 1
        every { orderRepository.save(any()) } returns mockk()
        every { orderReceivedMessenger.send(any()) } returns Unit

        every { orderDomainMapper.new(any<OrderOutboundResponse>()) } returns success(mockOrder())
        every { orderDomainMapper.new(any(), any(), any()) } returns success(mockk())
        every { orderDomainMapper.new(any(), any(), any(), any(), any(), any(), any()) } returns success(mockk())
    }

    @When("Cliente informa cadastro existente")
    fun `cliente informa cadastro existente`() {
        val cpf = "92979654078"

        val createOrderItemsInboundRequest = listOf(CreateOrderItemInboundRequest("", 1))

        // customer
        every { findCustomerService.find(any(), any()) } returns success(mockCustomer(cpf))

        // order items
        every { findProductService.find(any(), any()) } returns success(mockk())

        val createOrderInboundRequest = CreateOrderInboundRequest(cpf, createOrderItemsInboundRequest)

        createOrderService.create(createOrderInboundRequest)
    }

    @When("Cliente informa cadastro inexistente")
    fun `cliente informa cadastro inexistente`() {
        val cpf = "92979654078"

        val createOrderItemsInboundRequest = listOf(CreateOrderItemInboundRequest("", 1))

        // customer
        every { findCustomerService.find(any(), any()) } returns failure(mockk())

        val createOrderInboundRequest = CreateOrderInboundRequest(cpf, createOrderItemsInboundRequest)

        createOrderService.create(createOrderInboundRequest)
    }

    @When("Cliente nao informa cadastro")
    fun `cliente nao informa cadastro`() {
        val createOrderItemsInboundRequest = listOf(CreateOrderItemInboundRequest("", 1))

        // order items
        every { findProductService.find(any(), any()) } returns success(mockk())

        val createOrderInboundRequest = CreateOrderInboundRequest(null, createOrderItemsInboundRequest)

        createOrderService.create(createOrderInboundRequest)
    }

    @When("Cliente informa produto inexistente")
    fun `cliente informa produto inexistente`() {
        val cpf = "92979654078"

        val createOrderItemsInboundRequest = listOf(CreateOrderItemInboundRequest("", 1))

        // customer
        every { findCustomerService.find(any(), any()) } returns success(mockCustomer(cpf))

        // order items
        every { findProductService.find(any(), any()) } returns failure(mockk())

        val createOrderInboundRequest = CreateOrderInboundRequest(cpf, createOrderItemsInboundRequest)

        createOrderService.create(createOrderInboundRequest)
    }

    @Then("Salva pedido")
    fun `salva pedido`() {
        verify(exactly = 1) { orderRepository.save(any()) }
    }

    @Then("Nao salva pedido")
    fun `nao salva pedido`() {
        verify(exactly = 0) { orderRepository.save(any()) }
    }

    @Then("Notifica que pedido foi recebido")
    fun `notifica que pedido foi recebido`() {
        verify(exactly = 1) { orderReceivedMessenger.send(any()) }
    }

    @Then("Nao notifica que pedido foi recebido")
    fun `nao notifica que pedido foi recebido`() {
        verify(exactly = 0) { orderReceivedMessenger.send(any()) }
    }

    private fun mockCustomer(cpf: String) =
        mockk<Customer>().also {
            every { it.cpf } returns Cpf.new(cpf).getOrThrow()
        }

    private fun mockOrder() =
        mockk<Order>().also {
            val orderId = UUID.randomUUID().toString()
            val price = BigDecimal.ONE

            every { it.id } returns OrderId.new(orderId).getOrThrow()
            every { it.price } returns OrderPrice.new(price).getOrThrow()
        }
}
