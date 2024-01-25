package com.mikes.pedido.application.core.usecase.exception.customer

import com.mikes.pedido.application.core.usecase.exception.AlreadyExistsException

class CustomerAlreadyExistsException(message: String) : AlreadyExistsException(TYPE, message) {
    companion object {
        private const val TYPE = "Customer"
    }
}
