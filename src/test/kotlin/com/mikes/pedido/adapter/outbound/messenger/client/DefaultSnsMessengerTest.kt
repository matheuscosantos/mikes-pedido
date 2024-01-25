package com.mikes.pedido.adapter.outbound.messenger.client

import com.mikes.pedido.adapter.outbound.messenger.sns.client.DefaultSnsMessenger
import com.mikes.pedido.util.serializeJson
import io.awspring.cloud.sns.core.SnsTemplate
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class DefaultSnsMessengerTest {

    @Test
    fun `test default sns messenger`() {
        val snsTemplate = mockk<SnsTemplate>()

        val defaultSnsMessenger = DefaultSnsMessenger(snsTemplate)

        val arn = "arn:aws:sns:us-east-1:123456789012:test-topic"
        val message = TestMessage(data = "teste")

        every { snsTemplate.send(arn, any()) } returns Unit

        defaultSnsMessenger.send(arn, message)

        verify { snsTemplate.send(arn, match { it.payload == message.serializeJson() }) }
    }

    data class TestMessage(val data: String)
}
