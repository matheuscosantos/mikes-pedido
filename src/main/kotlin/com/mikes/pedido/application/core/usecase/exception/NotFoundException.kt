package com.mikes.pedido.application.core.usecase.exception

open class NotFoundException(val type: String, message: String) : Exception(message)
