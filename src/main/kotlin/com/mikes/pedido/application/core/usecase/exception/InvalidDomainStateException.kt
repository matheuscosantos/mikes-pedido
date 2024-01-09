package com.mikes.pedido.application.core.usecase.exception

open class InvalidDomainStateException(val type: String, message: String) : Exception(message)
