package com.mikes.pedido.application.core.domain.exception.order

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidOrderItemPriceException(message: String) : InvalidValueException(TYPE, message) {

    companion object {
        private const val TYPE = "OrderItemPrice"
    }
}
