package com.mikes.pedido.application.core.usecase.exception.order

import com.mikes.pedido.application.core.usecase.exception.InvalidDomainStateException

class InvalidOrderStateException(message: String) : InvalidDomainStateException(TYPE, message) {
    companion object {
        private const val TYPE = "Order"
    }
}
