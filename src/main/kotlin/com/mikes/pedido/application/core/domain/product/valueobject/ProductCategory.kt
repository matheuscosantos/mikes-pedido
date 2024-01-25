package com.mikes.pedido.application.core.domain.product.valueobject

import com.mikes.pedido.application.core.domain.exception.product.InvalidProductCategoryException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

enum class ProductCategory {
    SNACK,
    DRINK,
    DESSERT,
    SIDE_DISH,
    ;

    companion object {
        fun new(name: String): Result<ProductCategory> =
            entries.find { it.name.equals(name, ignoreCase = true) }
                ?.let { success(it) }
                ?: failure(InvalidProductCategoryException("$name is not a valid ProductCategory."))
    }
}
