package com.mikes.pedido.application.core.domain.exception

open class InvalidValueException(val type: String, message: String) : Exception(message)
