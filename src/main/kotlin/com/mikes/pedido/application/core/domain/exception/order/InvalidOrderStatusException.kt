package com.mikes.pedido.application.core.domain.exception.order

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidOrderStatusException(message: String) : InvalidValueException(TYPE, message) {

    companion object {
        private const val TYPE = "OrderStatus"
    }
}
