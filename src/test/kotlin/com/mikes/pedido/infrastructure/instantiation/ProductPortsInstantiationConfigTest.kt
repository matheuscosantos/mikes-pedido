package com.mikes.pedido.infrastructure.instantiation

import com.mikes.pedido.adapter.outbound.database.ProductDatabaseRepository
import com.mikes.pedido.application.core.usecase.product.CreateProductUseCase
import com.mikes.pedido.application.core.usecase.product.DeleteProductUseCase
import com.mikes.pedido.application.core.usecase.product.FindProductUseCase
import com.mikes.pedido.application.core.usecase.product.UpdateProductUseCase
import com.mikes.pedido.application.mapper.product.DefaultProductDomainMapper
import io.mockk.mockk
import org.junit.jupiter.api.Test
import util.AssertionsUtil

internal class ProductPortsInstantiationConfigTest {
    @Test
    fun `when instantiating productDomainMapper, expect DefaultProductDomainMapper instance`() {
        val productDomainMapper = ProductPortsInstantiationConfig().productDomainMapper()

        AssertionsUtil.isInstanceType<DefaultProductDomainMapper>(productDomainMapper)
    }

    @Test
    fun `when instantiating productRepository, expect ProductDatabaseRepository instance`() {
        val productRepository = ProductPortsInstantiationConfig().productRepository(mockk())

        AssertionsUtil.isInstanceType<ProductDatabaseRepository>(productRepository)
    }

    @Test
    fun `when instantiating createProductService, expect CreateProductUseCase instance`() {
        val createProductService = ProductPortsInstantiationConfig().createProductService(mockk(), mockk())

        AssertionsUtil.isInstanceType<CreateProductUseCase>(createProductService)
    }

    @Test
    fun `when instantiating updateProductService, expect UpdateProductUseCase instance`() {
        val updateProductService = ProductPortsInstantiationConfig().updateProductService(mockk(), mockk())

        AssertionsUtil.isInstanceType<UpdateProductUseCase>(updateProductService)
    }

    @Test
    fun `when instantiating deleteProductService, expect DeleteProductUseCase instance`() {
        val deleteProductService = ProductPortsInstantiationConfig().deleteProductService(mockk(), mockk())

        AssertionsUtil.isInstanceType<DeleteProductUseCase>(deleteProductService)
    }

    @Test
    fun `when instantiating findProductService, expect FindProductUseCase instance`() {
        val findProductService = ProductPortsInstantiationConfig().findProductService(mockk(), mockk())

        AssertionsUtil.isInstanceType<FindProductUseCase>(findProductService)
    }
}
