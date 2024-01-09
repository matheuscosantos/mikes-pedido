package com.mikes.pedido.application.core.usecase.exception.customer

import com.mikes.pedido.application.core.usecase.exception.NotFoundException

class CustomerNotFoundException(message: String) : NotFoundException(TYPE, message) {
    companion object {
        private const val TYPE = "Customer"
    }
}
