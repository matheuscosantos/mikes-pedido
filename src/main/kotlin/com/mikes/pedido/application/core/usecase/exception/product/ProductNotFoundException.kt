package com.mikes.pedido.application.core.usecase.exception.product

import com.mikes.pedido.application.core.usecase.exception.NotFoundException

class ProductNotFoundException(message: String) : NotFoundException(TYPE, message) {
    companion object {
        private const val TYPE = "Product"
    }
}
