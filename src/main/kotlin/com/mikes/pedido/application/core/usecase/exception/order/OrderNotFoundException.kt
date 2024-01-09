package com.mikes.pedido.application.core.usecase.exception.order

import com.mikes.pedido.application.core.usecase.exception.NotFoundException

class OrderNotFoundException(message: String) : NotFoundException(TYPE, message) {
    companion object {
        private const val TYPE = "Order"
    }
}
