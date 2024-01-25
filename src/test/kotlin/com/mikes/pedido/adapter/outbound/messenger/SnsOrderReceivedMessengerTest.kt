package com.mikes.pedido.adapter.outbound.messenger

import com.mikes.pedido.adapter.outbound.messenger.sns.SnsOrderReceivedMessenger
import com.mikes.pedido.adapter.outbound.messenger.sns.client.SnsMessenger
import com.mikes.pedido.application.port.outbound.order.dto.OrderReceivedMessage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class SnsOrderReceivedMessengerTest {

    @Test
    fun `sns order received messenger test`() {
        val snsMessenger = mockk<SnsMessenger>()
        val orderReceivedArn = "arn:aws:sns:us-east-1:123456789012:order-received"

        val snsOrderReceivedMessenger = SnsOrderReceivedMessenger(snsMessenger, orderReceivedArn)

        val orderReceivedMessage = OrderReceivedMessage(orderId = "id", value = BigDecimal.valueOf(12))

        every { snsMessenger.send(any(), any()) } returns Unit

        snsOrderReceivedMessenger.send(orderReceivedMessage)

        verify { snsMessenger.send(orderReceivedArn, orderReceivedMessage) }
    }
}
