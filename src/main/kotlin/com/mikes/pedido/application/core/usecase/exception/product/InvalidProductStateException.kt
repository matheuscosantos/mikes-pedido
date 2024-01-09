package com.mikes.pedido.application.core.usecase.exception.product

import com.mikes.pedido.application.core.usecase.exception.InvalidDomainStateException

class InvalidProductStateException(message: String) : InvalidDomainStateException(TYPE, message) {
    companion object {
        private const val TYPE = "Product"
    }
}
