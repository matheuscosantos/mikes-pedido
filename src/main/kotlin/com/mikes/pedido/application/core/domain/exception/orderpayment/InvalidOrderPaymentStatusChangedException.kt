package com.mikes.pedido.application.core.domain.exception.orderpayment

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidOrderPaymentStatusChangedException(message: String) : InvalidValueException(TYPE, message) {
    companion object {
        private const val TYPE = "OrderPaymentStatus"
    }
}
