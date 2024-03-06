package com.mikes.pedido.application.port.inbound.customer

interface DeleteCustomerService {
    fun delete(idValue: String): Result<Unit>
}
