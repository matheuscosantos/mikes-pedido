package com.mikes.pedido.infrastructure.instantiation

import com.mikes.pedido.adapter.outbound.database.CustomerDatabaseRepository
import com.mikes.pedido.application.core.usecase.customer.CreateCustomerUseCase
import com.mikes.pedido.application.core.usecase.customer.FindCustomerUseCase
import com.mikes.pedido.application.mapper.customer.DefaultCustomerDomainMapper
import io.mockk.mockk
import org.junit.jupiter.api.Test
import util.AssertionsUtil

internal class CustomerPortsInstantiationConfigTest {
    @Test
    fun `when instantiating customerDomainMapper, expect DefaultCustomerDomainMapper instance`() {
        val customerDomainMapper = CustomerPortsInstantiationConfig().customerDomainMapper()

        AssertionsUtil.isInstanceType<DefaultCustomerDomainMapper>(customerDomainMapper)
    }

    @Test
    fun `when instantiating customerRepository, expect CustomerDatabaseRepository instance`() {
        val customerRepository = CustomerPortsInstantiationConfig().customerRepository(mockk(), mockk())

        AssertionsUtil.isInstanceType<CustomerDatabaseRepository>(customerRepository)
    }

    @Test
    fun `when instantiating createCustomerService, expect CreateCustomerUseCase instance`() {
        val createCustomerService = CustomerPortsInstantiationConfig().createCustomerService(mockk(), mockk())

        AssertionsUtil.isInstanceType<CreateCustomerUseCase>(createCustomerService)
    }

    @Test
    fun `when instantiating findCustomerService, expect FindCustomerUseCase instance`() {
        val findCustomerService = CustomerPortsInstantiationConfig().findCustomerService(mockk(), mockk())

        AssertionsUtil.isInstanceType<FindCustomerUseCase>(findCustomerService)
    }
}
