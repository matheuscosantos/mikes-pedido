package com.mikes.pedido.adapter.outbound.messenger.sns.client

import com.mikes.pedido.util.serializeJson
import io.awspring.cloud.sns.core.SnsTemplate
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Service

@Service
class DefaultSnsMessenger(
    private val snsTemplate: SnsTemplate,
) : SnsMessenger {
    override fun <M : Any> send(
        arn: String,
        message: M,
    ) {
        snsTemplate.send(arn, GenericMessage(message.serializeJson()))
    }
}
