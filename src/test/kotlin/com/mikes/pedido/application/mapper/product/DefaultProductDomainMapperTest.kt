package com.mikes.pedido.application.mapper.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductDescription
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import com.mikes.pedido.application.port.inbound.product.dto.CreateProductInboundRequest
import com.mikes.pedido.application.port.inbound.product.dto.UpdateProductInboundRequest
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime.now

internal class DefaultProductDomainMapperTest {
    @Test
    fun `when creating product with CreateProductInboundRequest, expect attribute equality`() {
        val name = "name"
        val price = BigDecimal.ONE
        val category = ProductCategory.DRINK.name
        val description = "description"
        val productId = ProductId.generate()
        val active = true
        val createdAt = now()
        val updatedAt = now()

        val product =
            DefaultProductDomainMapper().new(
                CreateProductInboundRequest(
                    name,
                    price,
                    category,
                    description,
                ),
                productId,
                active,
                createdAt,
                updatedAt,
            ).getOrThrow()

        with(product) {
            Assertions.assertEquals(name, this.name.value)
            Assertions.assertEquals(price, this.price.value)
            Assertions.assertEquals(category, this.category.name)
            Assertions.assertEquals(description, this.description.value)
            Assertions.assertEquals(productId, this.id)
            Assertions.assertEquals(active, this.active)
            Assertions.assertEquals(createdAt, this.createdAt)
            Assertions.assertEquals(updatedAt, this.updatedAt)
        }
    }

    @Test
    fun `when creating product with ProductOutboundResponse, expect attribute equality`() {
        val id = ProductId.generate().value
        val name = "name"
        val price = BigDecimal.ONE
        val category = ProductCategory.DRINK.name
        val description = "description"
        val active = true
        val createdAt = now()
        val updatedAt = now()

        val product =
            DefaultProductDomainMapper().new(
                ProductOutboundResponse(
                    id,
                    name,
                    price,
                    category,
                    description,
                    active,
                    createdAt,
                    updatedAt,
                ),
            ).getOrThrow()

        with(product) {
            Assertions.assertEquals(id, this.id.value)
            Assertions.assertEquals(name, this.name.value)
            Assertions.assertEquals(price, this.price.value)
            Assertions.assertEquals(category, this.category.name)
            Assertions.assertEquals(description, this.description.value)
            Assertions.assertEquals(active, this.active)
            Assertions.assertEquals(createdAt, this.createdAt)
            Assertions.assertEquals(updatedAt, this.updatedAt)
        }
    }

    @Test
    fun `when updating product with UpdateProductInboundRequest, expect attribute equality`() {
        val productId = ProductId.generate()
        val productName = ProductName.new("name").getOrThrow()
        val productPrice = ProductPrice.new(BigDecimal.ONE).getOrThrow()
        val productCategory = ProductCategory.DRINK
        val productDescription = ProductDescription.new("description").getOrThrow()
        val active = true
        val createdAt = now()
        val updatedAt = now()

        val actualProduct =
            Product.new(
                productId,
                productName,
                productPrice,
                productCategory,
                productDescription,
                active,
                createdAt,
                updatedAt,
            ).getOrThrow()

        val product =
            DefaultProductDomainMapper().update(
                UpdateProductInboundRequest(
                    productName.value,
                    productDescription.value,
                    productPrice.value,
                    productCategory.name,
                ),
                actualProduct,
            ).getOrThrow()

        with(product) {
            Assertions.assertEquals(productId, this.id)
            Assertions.assertEquals(productName, this.name)
            Assertions.assertEquals(productPrice, this.price)
            Assertions.assertEquals(productCategory, this.category)
            Assertions.assertEquals(productDescription, this.description)
            Assertions.assertEquals(active, this.active)
            Assertions.assertEquals(createdAt, this.createdAt)
        }
    }
}
