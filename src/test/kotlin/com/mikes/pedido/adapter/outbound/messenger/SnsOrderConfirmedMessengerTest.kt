package com.mikes.pedido.adapter.outbound.messenger
import com.mikes.pedido.adapter.outbound.messenger.sns.SnsOrderConfirmedMessenger
import com.mikes.pedido.adapter.outbound.messenger.sns.client.SnsMessenger
import com.mikes.pedido.application.port.outbound.order.dto.OrderConfirmedMessage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class SnsOrderConfirmedMessengerTest {

    @Test
    fun `test sns order confirmed messenger`() {
        val snsMessenger = mockk<SnsMessenger>()
        val orderConfirmedArn = "arn:aws:sns:us-east-1:123456789012:order-confirmed"

        val snsOrderConfirmedMessenger = SnsOrderConfirmedMessenger(snsMessenger, orderConfirmedArn)

        val orderConfirmedMessage = OrderConfirmedMessage("1")

        every { snsMessenger.send(any(), any()) } returns Unit

        snsOrderConfirmedMessenger.send(orderConfirmedMessage)

        verify { snsMessenger.send(orderConfirmedArn, orderConfirmedMessage) }
    }
}
