package main

/**
 * Represents a JSON null value.
 */
data object JsonNull : JsonValue {
    override fun toJsonString(): String = "null"

    override fun isValidType(validator: JsonValidate) {
        validator.validate(this)
    }
}