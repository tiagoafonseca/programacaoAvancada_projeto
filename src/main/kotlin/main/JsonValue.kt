package main

/**
 * Base interface for all JSON values.
 */
sealed interface JsonValue {
    fun toJsonString(): String
    fun isValidType(validator: JsonValidate)
}



