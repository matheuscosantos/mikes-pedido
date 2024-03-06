package com.mikes.pedido.infrastructure.instantiation

import com.mikes.pedido.adapter.outbound.database.CustomerDatabaseRepository
import com.mikes.pedido.adapter.outbound.database.jpa.CustomerJpaRepository
import com.mikes.pedido.application.core.usecase.customer.CreateCustomerUseCase
import com.mikes.pedido.application.core.usecase.customer.DeleteCustomerUseCase
import com.mikes.pedido.application.core.usecase.customer.FindCustomerUseCase
import com.mikes.pedido.application.mapper.customer.CustomerDomainMapper
import com.mikes.pedido.application.mapper.customer.DefaultCustomerDomainMapper
import com.mikes.pedido.application.port.inbound.customer.CreateCustomerService
import com.mikes.pedido.application.port.inbound.customer.DeleteCustomerService
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.outbound.customer.CustomerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomerPortsInstantiationConfig {
    @Bean
    fun customerDomainMapper(): CustomerDomainMapper {
        return DefaultCustomerDomainMapper()
    }

    @Bean
    fun customerRepository(
        customerJpaRepository: CustomerJpaRepository,
        customerDomainMapper: CustomerDomainMapper,
    ): CustomerRepository {
        return CustomerDatabaseRepository(customerJpaRepository)
    }

    @Bean
    fun createCustomerService(
        customerRepository: CustomerRepository,
        customerDomainMapper: CustomerDomainMapper,
    ): CreateCustomerService {
        return CreateCustomerUseCase(customerRepository, customerDomainMapper)
    }

    @Bean
    fun findCustomerService(
        customerRepository: CustomerRepository,
        customerDomainMapper: CustomerDomainMapper,
    ): FindCustomerService {
        return FindCustomerUseCase(customerRepository, customerDomainMapper)
    }

    @Bean
    fun deleteCustomerService(
        findCustomerService: FindCustomerService,
        customerRepository: CustomerRepository,
    ): DeleteCustomerService {
        return DeleteCustomerUseCase(
            findCustomerService,
            customerRepository,
        )
    }
}
