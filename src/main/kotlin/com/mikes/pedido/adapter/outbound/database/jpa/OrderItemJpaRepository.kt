package com.mikes.pedido.adapter.outbound.database.jpa

import com.mikes.pedido.adapter.outbound.database.entity.OrderItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderItemJpaRepository : JpaRepository<OrderItemEntity, String> {
    fun findByOrderId(orderId: String): List<OrderItemEntity>
}