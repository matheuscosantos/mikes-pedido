package com.mikes.pedido.application.core.domain.exception.product

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidProductDescriptionException(message: String) : InvalidValueException(TYPE, message) {

    companion object {
        private const val TYPE = "ProductDescription"
    }
}
