package com.mikes.pedido.adapter.outbound.database.jpa

import com.mikes.pedido.adapter.outbound.database.entity.OrderPaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface OrderPaymentJpaRepository : JpaRepository<OrderPaymentEntity, String> {

    fun findByOrderId(orderId: String): Optional<OrderPaymentEntity>
}
