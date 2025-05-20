package main

fun main() {
    val obj = JsonObject(mapOf(
        "name" to JsonString("Alice"),
        "age" to JsonNumber(30),
        "active" to JsonBoolean(true)
    ));
    println(obj.toJsonString())

    fun validateJson(json: JsonValue): Boolean {
        val validator = JsonBaseValidator()
        json.isValidType(validator)
        return validator.isValid
    }

    val validator = validateJson(JsonString("Hello World"))
    println(validator)
}