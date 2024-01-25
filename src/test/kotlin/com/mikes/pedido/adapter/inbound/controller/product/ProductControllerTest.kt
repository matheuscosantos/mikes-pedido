package com.mikes.pedido.adapter.inbound.controller.product
import com.mikes.pedido.adapter.inbound.controller.product.dto.CreateProductRequest
import com.mikes.pedido.adapter.inbound.controller.product.dto.UpdateProductRequest
import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductDescription
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import com.mikes.pedido.application.port.inbound.product.CreateProductService
import com.mikes.pedido.application.port.inbound.product.DeleteProductService
import com.mikes.pedido.application.port.inbound.product.FindProductService
import com.mikes.pedido.application.port.inbound.product.UpdateProductService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDateTime

class ProductControllerTest {

    private lateinit var createProductService: CreateProductService
    private lateinit var updateProductService: UpdateProductService
    private lateinit var deleteProductService: DeleteProductService
    private lateinit var findProductService: FindProductService
    private lateinit var productController: ProductController

    @BeforeEach
    fun setUp() {
        createProductService = mockk(relaxed = true)
        updateProductService = mockk(relaxed = true)
        deleteProductService = mockk(relaxed = true)
        findProductService = mockk(relaxed = true)
        productController = ProductController(createProductService, updateProductService, deleteProductService, findProductService)
    }

    @Test
    fun `test create product`() {
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

        val request = CreateProductRequest(
            "Test Product",
            BigDecimal(10.00),
            "drink",
            "Description",
        )
        every { createProductService.create(any()) } returns Result.success(product.getOrThrow())

        val response = productController.create(request)

        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun `test update product`() {
        val id = "productId"
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

        val request = UpdateProductRequest(
            name = "teste",
            description = "description",
            price = BigDecimal.valueOf(1),
            category = "drink",
        )
        every { updateProductService.update(id, any()) } returns Result.success(product.getOrThrow())

        val response = productController.update(id, request)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `test delete product`() {
        val id = "productId"
        every { deleteProductService.delete(id) } returns Result.success(Unit)

        val response = productController.delete(id)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun `test find by category`() {
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
        val category = "drink"
        every { findProductService.findAll(category, true) } returns Result.success(listOf(product.getOrThrow()))

        val response = productController.findByCategory(category, true)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    companion object {
        private val date = LocalDateTime.now()
    }
}
