fun main() {
    val obj = JsonObject(mapOf("name" to JsonString("Alice")))
    println(obj.toJsonString())

    fun validateJson(json: JsonValue): Boolean {
        val validator = JsonBaseValidator()
        json.isValidType(validator)
        return validator.isValid
    }

    val validator = validateJson(JsonString("Hello World"))
    println(validator)
}