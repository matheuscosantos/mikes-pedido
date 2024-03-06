package com.mikes.pedido.application.port.inbound.customer

import com.mikes.pedido.application.core.domain.customer.Customer

fun interface FindCustomerService {
    fun find(customerIdValue: String): Result<Customer>
}
