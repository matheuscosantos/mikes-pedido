package com.mikes.pedido.application.core.usecase.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class CreateProductUseCaseTest {
    @Test
    fun `when creating a product with success, expect creation`() {
        val expectedProduct = mockk<Product>()

        val productRepository = mockProductRepository()
        val productDomainMapper = mockProductDomainMapper(expectedProduct)

        val createProductUseCase = CreateProductUseCase(productRepository, productDomainMapper)

        val product =
            createProductUseCase.create(mockk())
                .getOrThrow()

        Assertions.assertEquals(expectedProduct, product)
    }

    private fun mockProductRepository() =
        mockk<ProductRepository>().also {
            every { it.save(any()) } returns mockk()
        }

    private fun mockProductDomainMapper(product: Product) =
        mockk<ProductDomainMapper>().also {
            every { it.new(any()) } returns success(product)
            every { it.new(any(), any(), any(), any(), any()) } returns success(mockk())
        }
}
