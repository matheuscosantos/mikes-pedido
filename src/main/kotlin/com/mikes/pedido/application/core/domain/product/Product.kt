package com.mikes.pedido.application.core.domain.product

import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductDescription
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import java.time.LocalDateTime
import kotlin.Result.Companion.success

class Product private constructor(
    val id: ProductId,
    val name: ProductName,
    val price: ProductPrice,
    val category: ProductCategory,
    val description: ProductDescription,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    companion object {
        fun new(
            id: ProductId,
            name: ProductName,
            price: ProductPrice,
            category: ProductCategory,
            description: ProductDescription,
            active: Boolean,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
        ): Result<Product> = success(Product(id, name, price, category, description, active, createdAt, updatedAt))
    }

    fun toInactive(): Product {
        val active = false
        val updatedAt = LocalDateTime.now()
        return Product(id, name, price, category, description, active, createdAt, updatedAt)
    }

    fun updateValues(
        name: ProductName,
        price: ProductPrice,
        category: ProductCategory,
        description: ProductDescription,
    ): Product {
        val updatedAt = LocalDateTime.now()
        return Product(id, name, price, category, description, active, createdAt, updatedAt)
    }
}
