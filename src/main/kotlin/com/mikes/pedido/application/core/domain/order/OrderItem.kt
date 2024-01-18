package com.mikes.pedido.application.core.domain.order

import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemId
import com.fasterxml.jackson.annotation.JsonProperty
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemPrice
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemQuantity
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import java.io.Serializable
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class OrderItem private constructor(
    @JsonProperty("id")
    val id: OrderItemId,
    @JsonProperty("productName")
    val productName: ProductName,
    @JsonProperty("productPrice")
    val productPrice: ProductPrice,
    @JsonProperty("quantity")
    val quantity: OrderItemQuantity,
    @JsonProperty("price")
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

        private fun calculatePrice(productPrice: ProductPrice, quantity: OrderItemQuantity): Result<OrderItemPrice> {
            val priceValue = productPrice.value * quantity.value.toBigDecimal()
            return OrderItemPrice.new(priceValue)
        }
    }
}
