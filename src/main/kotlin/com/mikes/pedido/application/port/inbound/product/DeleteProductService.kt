package com.mikes.pedido.application.port.inbound.product

fun interface DeleteProductService {
    fun delete(idValue: String): Result<Unit>
}
