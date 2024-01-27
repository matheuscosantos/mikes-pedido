package com.mikes.pedido.application.core.usecase.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class DeleteProductUseCaseTest {
    @Test
    fun `when deleting a product, update to inactive and save that`() {
        val productIdValue = ProductId.generate().value
        val product = mockProduct()

        val productRepository = mockProductRepository()
        val productDomainMapper = mockProductDomainMapper(product)

        val deleteProductUseCase = DeleteProductUseCase(productRepository, productDomainMapper)

        deleteProductUseCase.delete(productIdValue)

        verify { product.toInactive() }
        verify { productRepository.save(any()) }
    }

    private fun mockProductRepository() =
        mockk<ProductRepository>().also {
            every { it.find(any(), any()) } returns mockk()
            every { it.save(any()) } returns mockk()
        }

    private fun mockProductDomainMapper(product: Product) =
        mockk<ProductDomainMapper>().also {
            every { it.new(any()) } returns success(product)
        }

    private fun mockProduct() =
        mockk<Product>().also {
            every { it.toInactive() } returns it
        }
}
