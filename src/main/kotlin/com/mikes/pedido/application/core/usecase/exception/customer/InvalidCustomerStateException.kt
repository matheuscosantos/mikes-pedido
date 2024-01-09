package com.mikes.pedido.application.core.usecase.exception.customer

import com.mikes.pedido.application.core.usecase.exception.InvalidDomainStateException

class InvalidCustomerStateException(message: String) : InvalidDomainStateException(TYPE, message) {
    companion object {
        private const val TYPE = "Customer"
    }
}
