package main

/**
 * Represents a JSON object.
 */
data class JsonObject(val entries: Map<String, JsonValue>) : JsonValue {
    override fun toJsonString(): String {
        return entries.entries.joinToString(
            prefix = "{", postfix = "}", separator = ","
        ) { (key, value) ->
            "\"${key.replace("\"", "\\\"")}\":${value.toJsonString()}"
        }
    }
    fun filter(predicate: (String, JsonValue) -> Boolean): JsonObject {
        val filteredElements = entries.filter { (key, value) -> predicate(key, value) }.toMap()
        return JsonObject(filteredElements)
    }

    override fun isValidType(validator: JsonValidate) {
        validator.validate(this)
        entries.values.forEach { it.isValidType(validator) }
    }
}