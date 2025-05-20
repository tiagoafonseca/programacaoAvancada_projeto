package main
class JsonBaseValidator: JsonValidate {

    var isValid: Boolean = true
        private set

    override fun validate(value: JsonString) {}
    override fun validate(value: JsonNumber) {}
    override fun validate(value: JsonBoolean) {}
    override fun validate(value: JsonNull) {}

    override fun validate(value: JsonArray) {
        // Recolhe os tipos dos elementos (ignorando nulls)
        val nonNullTypes = value.elements
            .filterNot { it is JsonNull }
            .map { it::class }

        if (nonNullTypes.isNotEmpty()) {
            val firstType = nonNullTypes.first()
            val allSameType = nonNullTypes.all { it == firstType }

            if (!allSameType) {
                isValid = false
            }
        }

        // Continua visita recursiva
        value.elements.forEach { it.isValidType(this) }
    }

    override fun validate(value: JsonObject) {
        // Validar as chaves
        for (key in value.entries.keys) {
            if (key.isBlank()) {
                isValid = false
                return
            }
        }
    }
}