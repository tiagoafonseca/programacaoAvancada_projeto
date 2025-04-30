sealed interface JsonValidator {
    fun validate(value: JsonString)
    fun validate(value: JsonNumber)
    fun validate(value: JsonBoolean)
    fun validate(value: JsonNull)
    fun validate(value: JsonArray)
    fun validate(value: JsonObject)
}