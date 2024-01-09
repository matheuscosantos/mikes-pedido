package com.mikes.pedido.application.core.domain.exception.orderpayment

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidOrderPaymentStatusException(message: String) : InvalidValueException(TYPE, message) {

    companion object {
        private const val TYPE = "OrderPaymentStatus"
    }
}
