package com.mikes.pedido.application.port.inbound.customer

import com.mikes.pedido.application.core.domain.customer.Customer

fun interface FindCustomerService {
    fun find(cpfValue: String, active: Boolean): Result<Customer>
}
