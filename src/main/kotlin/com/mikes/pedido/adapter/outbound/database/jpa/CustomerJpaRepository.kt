package com.mikes.pedido.adapter.outbound.database.jpa

import com.mikes.pedido.adapter.outbound.database.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerJpaRepository : JpaRepository<CustomerEntity, String>
