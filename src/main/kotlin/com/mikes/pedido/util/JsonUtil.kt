package com.mikes.pedido.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

private val objectMapper = Jackson2ObjectMapperBuilder().build<ObjectMapper>()

fun objectMapper() = objectMapper

fun <T : Any> T.serializeJson(): String = objectMapper().writeValueAsString(this)

inline fun <reified T : Any> String.deserializeJson(): T = objectMapper().readValue(this)
