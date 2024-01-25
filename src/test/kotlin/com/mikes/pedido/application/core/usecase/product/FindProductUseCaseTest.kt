package com.mikes.pedido.application.core.usecase.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class FindProductUseCaseTest {
    @Test
    fun `when find all found product, expect it`() {
        val productCategory = ProductCategory.DRINK.name
        val active = true
        val expectedProduct = mockk<Product>()

        val productRepository = mockProductRepository()
        val productDomainMapper = mockProductDomainMapper(expectedProduct)

        val findProductUseCase = FindProductUseCase(productRepository, productDomainMapper)

        val product =
            findProductUseCase.findAll(productCategory, active)
                .getOrThrow()
                .first()

        Assertions.assertEquals(expectedProduct, product)
    }

    @Test
    fun `when find found product, expect it`() {
        val id = ProductId.generate().value
        val active = true
        val expectedProduct = mockk<Product>()

        val productRepository = mockProductRepository()
        val productDomainMapper = mockProductDomainMapper(expectedProduct)

        val findProductUseCase = FindProductUseCase(productRepository, productDomainMapper)

        val product =
            findProductUseCase.find(id, active)
                .getOrThrow()

        Assertions.assertEquals(expectedProduct, product)
    }

    private fun mockProductRepository() =
        mockk<ProductRepository>().also {
            every { it.findByCategory(any(), any()) } returns listOf(mockk())
            every { it.find(any(), any()) } returns mockk()
        }

    private fun mockProductDomainMapper(product: Product) =
        mockk<ProductDomainMapper>().also {
            every { it.new(any<ProductOutboundResponse>()) } returns success(product)
        }
}
