package com.mikes.pedido.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class JsonUtilTest {
    @Test
    fun `when serializing json, expect attributes equality`() {
        val value = "test"
        val json = Json(value)

        val expectedSerializedJson = "{\"key\":\"$value\"}"
        val actualSerializedJson = json.serializeJson()

        Assertions.assertEquals(expectedSerializedJson, actualSerializedJson)
    }

    @Test
    fun `when deserializing json, expect attributes equality`() {
        val value = "test"
        val serializedJson = "{\"key\":\"$value\"}"

        val expectedJson = Json(value)
        val actualJson = serializedJson.deserializeJson<Json>()

        Assertions.assertEquals(expectedJson, actualJson)
    }

    private data class Json(val key: String)
}
