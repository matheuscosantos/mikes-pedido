package com.mikes.pedido.adapter.inbound.controller.customer

import com.mikes.pedido.adapter.inbound.controller.customer.dto.CreateCustomerRequest
import com.mikes.pedido.adapter.inbound.controller.customer.dto.CustomerDto
import com.mikes.pedido.application.port.inbound.customer.CreateCustomerService
import com.mikes.pedido.application.port.inbound.customer.DeleteCustomerService
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val createCustomerService: CreateCustomerService,
    private val findCustomerService: FindCustomerService,
    private val deleteCustomerService: DeleteCustomerService,
) {
    @PostMapping
    fun create(
        @RequestBody createCustomerRequest: CreateCustomerRequest,
    ): ResponseEntity<CustomerDto> {
        return createCustomerService.create(createCustomerRequest.toInbound())
            .map { CustomerDto.from(it) }
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }
            .getOrThrow()
    }

    @GetMapping("/{id}")
    fun find(
        @PathVariable id: String,
    ): ResponseEntity<CustomerDto> {
        return findCustomerService.find(id)
            .map { CustomerDto.from(it) }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String,
    ): ResponseEntity<Unit> {
        return deleteCustomerService.delete(id)
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }
}
