package com.mikes.pedido.util

import com.fasterxml.jackson.databind.ObjectMapper

private val objectMapper = ObjectMapper()

fun <T : Any> T.serializeJson(): String = objectMapper.writeValueAsString(this)
