package com.mikes.pedido.application.core.usecase.exception

open class AlreadyExistsException(val type: String, message: String) : Exception(message)
