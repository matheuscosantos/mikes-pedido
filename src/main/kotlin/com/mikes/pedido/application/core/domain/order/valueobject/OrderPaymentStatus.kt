package com.mikes.pedido.application.core.domain.order.valueobject

import com.mikes.pedido.application.core.domain.exception.order.InvalidOrderPaymentStatusException

enum class OrderPaymentStatus(val value: String) {
    ACCEPTED("accepted"),
    REFUSED("refused"),
    WAITING("waiting"),
    ;

    companion object {
        fun findByValue(value: String): Result<OrderPaymentStatus> {
            val status =
                entries.firstOrNull { it.value == value }
                    ?: return Result.failure(InvalidOrderPaymentStatusException("Invalid Order Payment Status: '$value'."))

            return Result.success(status)
        }
    }
}
