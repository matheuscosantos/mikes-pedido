package com.mikes.pedido.application.core.domain.exception.product

import com.mikes.pedido.application.core.domain.exception.InvalidValueException

class InvalidProductCategoryException(message: String) : InvalidValueException(TYPE, message) {
    companion object {
        private const val TYPE = "ProductCategory"
    }
}
