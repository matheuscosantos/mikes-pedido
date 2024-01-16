package com.mikes.pedido.application.core.domain.order.valueobject

import com.mikes.pedido.application.core.domain.exception.order.InvalidOrderStatusException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

enum class OrderStatus(val value: String) {
    RECEIVED("received"),
    PREPARING("preparing"),
    READY("ready"),
    FINISHED("finished"),
    CANCELLED("cancelled"),
    ;

    companion object {
        fun findByValue(value: String): Result<OrderStatus> {
            val orderStatus =
                entries.firstOrNull { it.value == value }
                    ?: return failure(InvalidOrderStatusException("Invalid Order Status: '$value'."))

            return success(orderStatus)
        }
    }
}
