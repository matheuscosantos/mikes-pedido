package com.mikes.pedido.adapter.outbound.database
import com.mikes.pedido.adapter.outbound.database.entity.ProductEntity
import com.mikes.pedido.adapter.outbound.database.jpa.ProductJpaRepository
import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductDescription
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class ProductDatabaseRepositoryTest {

    @Test
    fun testSave() {
        val productJpaRepository = mockk<ProductJpaRepository>()
        val productDatabaseRepository = ProductDatabaseRepository(productJpaRepository)

        val product = Product.new(
            id = ProductId.new("c3dc25a4-bdc6-49b5-9dd5-10d7418062b5").getOrThrow(),
            name = ProductName.new("teste").getOrThrow(),
            price = ProductPrice.new(BigDecimal.valueOf(1)).getOrThrow(),
            category = ProductCategory.DRINK,
            description = ProductDescription.new("description").getOrThrow(),
            active = true,
            createdAt = date,
            updatedAt = date,
        )
        val productEntity = ProductEntity.from(product.getOrThrow())

        every { productJpaRepository.save(any()) } returns productEntity

        val result = productDatabaseRepository.save(product.getOrThrow())

        assertNotNull(result)
    }

    @Test
    fun testFind() {
        val productJpaRepository = mockk<ProductJpaRepository>()
        val productDatabaseRepository = ProductDatabaseRepository(productJpaRepository)

        val productId = ProductId.new("e21b75ef-d513-4568-bc87-868f41072e6e")
        val active = true
        val productEntity = ProductEntity(
            id = "e21b75ef-d513-4568-bc87-868f41072e6e",
            name = "produto",
            price = BigDecimal.valueOf(1),
            category = "drink",
            description = "descricao",
            active = true,
            createdAt = date,
            updatedAt = date,
        )

        every { productJpaRepository.findByIdAndActive(any(), any()) } returns Optional.of(productEntity)

        val result = productDatabaseRepository.find(productId.getOrThrow(), active)

        assertNotNull(result)
    }

    @Test
    fun testFindByCategory() {
        val productJpaRepository = mockk<ProductJpaRepository>()
        val productDatabaseRepository = ProductDatabaseRepository(productJpaRepository)

        val productCategory = ProductCategory.new("drink")
        val active = true
        val productEntity = ProductEntity(
            id = "e21b75ef-d513-4568-bc87-868f41072e6e",
            name = "produto",
            price = BigDecimal.valueOf(1),
            category = "drink",
            description = "descricao",
            active = true,
            createdAt = date,
            updatedAt = date,
        )

        every { productJpaRepository.findAllByCategoryAndActive(any(), any()) } returns listOf(productEntity)

        val result = productDatabaseRepository.findByCategory(productCategory.getOrThrow(), active)

        assertNotNull(result)
    }

    companion object {
        private val date = LocalDateTime.now()
    }
}
