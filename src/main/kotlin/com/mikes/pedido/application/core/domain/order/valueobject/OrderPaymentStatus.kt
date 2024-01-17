package com.mikes.pedido.application.core.domain.order.valueobject

import com.mikes.pedido.application.core.domain.exception.order.InvalidOrderPaymentStatusException

enum class OrderPaymentStatus {
    ACCEPTED,
    REFUSED,
    WAITING,
    ;

    companion object {
        fun findByValue(nameValue: String): Result<OrderPaymentStatus> {
            val status =
                entries.find { it.name.equals(nameValue, ignoreCase = true) }
                    ?: return Result.failure(InvalidOrderPaymentStatusException("Invalid Order Payment Status: '$nameValue'."))

            return Result.success(status)
        }
    }
}
