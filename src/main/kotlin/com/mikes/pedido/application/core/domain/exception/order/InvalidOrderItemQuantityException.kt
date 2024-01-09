package com.mikes.pedido.application.core.domain.exception.order

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidOrderItemQuantityException(message: String) : InvalidValueException(TYPE, message) {

    companion object {
        private const val TYPE = "OrderItemQuantity"
    }
}
