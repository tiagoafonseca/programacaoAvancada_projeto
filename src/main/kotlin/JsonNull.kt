/**
 * Represents a JSON null value.
 */
data object JsonNull : JsonValue {
    override fun toJsonString(): String = "null"

    override fun isValidType(validator: JsonValidator) {
        validator.validate(this)
    }
}