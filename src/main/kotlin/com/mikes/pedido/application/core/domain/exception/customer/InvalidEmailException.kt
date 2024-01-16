package com.mikes.pedido.application.core.domain.exception.customer

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidEmailException(message: String) : InvalidValueException(TYPE, message) {
    companion object {
        private const val TYPE = "Email"
    }
}
