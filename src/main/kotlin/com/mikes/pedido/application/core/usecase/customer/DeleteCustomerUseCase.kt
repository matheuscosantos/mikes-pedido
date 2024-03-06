package com.mikes.pedido.application.core.usecase.customer

import com.mikes.pedido.application.port.inbound.customer.DeleteCustomerService
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.outbound.customer.CustomerRepository
import kotlin.Result.Companion.success

class DeleteCustomerUseCase(
    private val findCustomerService: FindCustomerService,
    private val customerRepository: CustomerRepository,
) : DeleteCustomerService {
    override fun delete(idValue: String): Result<Unit> {
        val customer =
            findCustomerService.find(idValue)
                .getOrElse { return Result.failure(it) }

        val deletedCustomer = customer.updatedToDeletion()

        customerRepository.save(deletedCustomer)

        return success(Unit)
    }
}
