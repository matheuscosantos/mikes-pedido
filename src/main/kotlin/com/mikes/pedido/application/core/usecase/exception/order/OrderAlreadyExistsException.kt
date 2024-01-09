package com.mikes.pedido.application.core.usecase.exception.order

import com.mikes.pedido.application.core.usecase.exception.AlreadyExistsException

class OrderAlreadyExistsException(message: String) : AlreadyExistsException(TYPE, message) {
    companion object {
        private const val TYPE = "Order"
    }
}
