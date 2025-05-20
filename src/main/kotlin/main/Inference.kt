package main

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun fromKotlinObject(obj: Any?): JsonValue {
    return when (obj) {
        null -> JsonNull
        is String -> JsonString(obj)
        is Int, is Long, is Float, is Double -> JsonNumber((obj as Number))
        is Boolean -> JsonBoolean(obj)
        is Enum<*> -> JsonString(obj.name)

        is List<*> -> {
            val elements = obj.map { fromKotlinObject(it) }
            JsonArray(elements)
        }

        is Map<*, *> -> {
            val map = obj.entries.associate { (k, v) ->
                require(k is String) { "Map keys must be strings." }
                k to fromKotlinObject(v)
            }
            JsonObject(map)
        }

        else -> {
            // Trata-se de uma data class (ou qualquer outro objeto)
            val kClass = obj::class
            val props = kClass.memberProperties.associate { prop ->
                prop.isAccessible = true
                val name = prop.name
                val value = prop.getter.call(obj)
                name to fromKotlinObject(value)
            }
            JsonObject(props)
        }
    }
}
