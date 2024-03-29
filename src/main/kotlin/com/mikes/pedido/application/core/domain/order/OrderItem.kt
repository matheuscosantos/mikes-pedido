package com.mikes.pedido.application.core.domain.order

import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemPrice
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemQuantity
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import java.io.Serializable
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class OrderItem private constructor(
    val id: OrderItemId,
    val productName: ProductName,
    val productPrice: ProductPrice,
    val quantity: OrderItemQuantity,
    val price: OrderItemPrice,
) : Serializable {
    companion object {
        fun new(
            id: OrderItemId,
            productName: ProductName,
            productPrice: ProductPrice,
            quantity: OrderItemQuantity,
        ): Result<OrderItem> {
            val price = calculatePrice(productPrice, quantity).getOrElse { return failure(it) }

            return success(OrderItem(id, productName, productPrice, quantity, price))
        }

        private fun calculatePrice(
            productPrice: ProductPrice,
            quantity: OrderItemQuantity,
        ): Result<OrderItemPrice> {
            val priceValue = productPrice.value * quantity.value.toBigDecimal()
            return OrderItemPrice.new(priceValue)
        }
    }
}
