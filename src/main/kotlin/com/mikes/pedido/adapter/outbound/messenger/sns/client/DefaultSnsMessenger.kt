package com.mikes.pedido.adapter.outbound.messenger.sns.client

import io.awspring.cloud.sns.core.SnsTemplate
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Service

@Service
class DefaultSnsMessenger(
    private val snsTemplate: SnsTemplate,
) : SnsMessenger {
    override fun <M : Any> send(
        topicName: String,
        message: M,
    ) {
        snsTemplate.send(topicName, GenericMessage(message))
    }
}
