package main

/**
 * Represents a JSON number value.
 */
data class JsonNumber(val value: Number) : JsonValue {
    override fun toJsonString(): String = value.toString()

    override fun isValidType(validator: JsonValidate) {
        validator.validate(this)
    }

}
