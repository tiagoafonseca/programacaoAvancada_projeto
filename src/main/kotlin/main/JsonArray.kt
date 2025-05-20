package main

/**
 * Represents a JSON array.
 */
data class JsonArray(val elements: List<JsonValue>) : JsonValue {
    override fun toJsonString(): String {
        return elements.joinToString(
            prefix = "[", postfix = "]", separator = ","
        ) { it.toJsonString() }
    }

    fun filter(predicate: (JsonValue) -> Boolean): JsonArray {
        val filteredElements = elements.filter(predicate)
        return JsonArray(filteredElements)
    }

    fun map(transform: (JsonValue) -> JsonValue): JsonArray {
        val mappedElements = elements.map(transform)
        return JsonArray(mappedElements)
    }

    override fun isValidType(validator: JsonValidate) {
        validator.validate(this)
        elements.forEach { it.isValidType(validator) }
    }
}