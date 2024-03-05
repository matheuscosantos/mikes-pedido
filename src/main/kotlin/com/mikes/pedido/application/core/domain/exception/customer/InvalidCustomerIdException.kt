package com.mikes.pedido.application.core.domain.exception.customer

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidCustomerIdException(message: String) : InvalidValueException(TYPE, message) {
    companion object {
        private const val TYPE = "CustomerId"
    }
}
