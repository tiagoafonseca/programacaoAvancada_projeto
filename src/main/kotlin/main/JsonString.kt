package main

/**
 * Represents a JSON text value ("Hello World").
 */
data class JsonString(val value: String) : JsonValue {
    override fun toJsonString(): String {
        return "\"" + value.replace("\"", "\\\"") + "\""
    }

    override fun isValidType(validator: JsonValidate) {
        validator.validate(this)
    }
}