package com.mikes.pedido.application.core.domain.customer.valueobject

import com.mikes.pedido.application.core.domain.exception.customer.InvalidCustomerIdException
import java.util.UUID
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class CustomerId private constructor(val value: String) {
    companion object {
        fun new(value: String): Result<CustomerId> {
            val uuid =
                runCatching { UUID.fromString(value) }
                    .getOrElse { return failure(InvalidCustomerIdException("invalid customer id.")) }

            return success(CustomerId(uuid.toString()))
        }

        fun generate() = CustomerId(UUID.randomUUID().toString())
    }
}
