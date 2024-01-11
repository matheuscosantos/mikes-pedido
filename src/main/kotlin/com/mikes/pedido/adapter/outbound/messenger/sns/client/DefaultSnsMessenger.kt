package com.mikes.pedido.adapter.outbound.messenger.sns.client

import io.awspring.cloud.messaging.core.NotificationMessagingTemplate
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Service

@Service
class DefaultSnsMessenger(
    private val messagingTemplate: NotificationMessagingTemplate,
) : SnsMessenger {
    override fun <M : Any> send(
        topicName: String,
        message: M,
    ) {
        messagingTemplate.send(topicName, GenericMessage(message))
    }
}
