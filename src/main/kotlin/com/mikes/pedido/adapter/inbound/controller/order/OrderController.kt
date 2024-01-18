package com.mikes.pedido.adapter.inbound.controller.order

import com.mikes.pedido.adapter.inbound.controller.order.dto.CreateOrderRequest
import com.mikes.pedido.adapter.inbound.controller.order.dto.OrderDto
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.port.inbound.order.CreateOrderService
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.util.flatMap
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    private val createOrderService: CreateOrderService,
    private val findOrderService: FindOrderService,
) {
    @GetMapping
    fun findOrdersWithDescriptions(): ResponseEntity<List<OrderDto>> {
        return findOrderService.findOrdersWithDescriptions()
            .map { it.map { order -> OrderDto.from(order) } }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }

    @PostMapping
    fun create(
        @RequestBody createOrderRequest: CreateOrderRequest,
    ): ResponseEntity<OrderDto> {
        return createOrderService.create(createOrderRequest.toInbound())
            .map { OrderDto.from(it) }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }

    @GetMapping("/{orderId}")
    @Cacheable(value = ["findOrdersWithDescriptions"], key = "#orderId")
    fun findOrdersById(@PathVariable orderId: String): OrderDto {
        return OrderId.new(orderId)
            .flatMap { findOrderService.find(it) }
            .map { OrderDto.from(it) }
            .getOrThrow()
    }
}
