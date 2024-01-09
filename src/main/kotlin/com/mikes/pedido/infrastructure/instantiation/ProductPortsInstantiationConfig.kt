package com.mikes.pedido.infrastructure.instantiation

import com.mikes.pedido.adapter.outbound.database.ProductDatabaseRepository
import com.mikes.pedido.adapter.outbound.database.jpa.ProductJpaRepository
import com.mikes.pedido.application.core.usecase.product.CreateProductUseCase
import com.mikes.pedido.application.core.usecase.product.DeleteProductUseCase
import com.mikes.pedido.application.core.usecase.product.FindProductUseCase
import com.mikes.pedido.application.core.usecase.product.UpdateProductUseCase
import com.mikes.pedido.application.mapper.product.DefaultProductDomainMapper
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductPortsInstantiationConfig {
    @Bean
    fun productDomainMapper(): ProductDomainMapper {
        return DefaultProductDomainMapper()
    }

    @Bean
    fun productRepository(productJpaRepository: ProductJpaRepository): ProductRepository {
        return ProductDatabaseRepository(productJpaRepository)
    }

    @Bean
    fun createProductService(
        productRepository: ProductRepository,
        productDomainMapper: ProductDomainMapper,
    ): CreateProductUseCase {
        return CreateProductUseCase(productRepository, productDomainMapper)
    }

    @Bean
    fun updateProductService(
        productRepository: ProductRepository,
        productDomainMapper: ProductDomainMapper,
    ): UpdateProductUseCase {
        return UpdateProductUseCase(productRepository, productDomainMapper)
    }

    @Bean
    fun deleteProductService(
        productRepository: ProductRepository,
        productDomainMapper: ProductDomainMapper,
    ): DeleteProductUseCase {
        return DeleteProductUseCase(productRepository, productDomainMapper)
    }

    @Bean
    fun findProductService(
        productRepository: ProductRepository,
        productDomainMapper: ProductDomainMapper,
    ): FindProductUseCase {
        return FindProductUseCase(productRepository, productDomainMapper)
    }
}
