package com.mikes.pedido.application.core.domain.order.valueobject

import com.mikes.pedido.application.core.domain.exception.order.InvalidOrderStatusException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

enum class OrderStatus {
    RECEIVED,
    PAID,
    PREPARING,
    READY,
    FINISHED,
    CANCELLED,
    ;

    companion object {
        fun findByValue(nameValue: String): Result<OrderStatus> {
            val orderStatus =
                entries.find { it.name.equals(nameValue, ignoreCase = true) }
                    ?: return failure(InvalidOrderStatusException("Invalid Order Status: '$nameValue'."))

            return success(orderStatus)
        }
    }
}
