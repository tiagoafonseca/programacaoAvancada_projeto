
class JsonValidatorVisitor: JsonVisitor {

    var isValid: Boolean = true
        private set

    override fun visit(value: JsonString) {}
    override fun visit(value: JsonNumber) {}
    override fun visit(value: JsonBoolean) {}
    override fun visit(value: JsonNull) {}

    override fun visit(value: JsonArray) {
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
        value.elements.forEach { it.accept(this) }
    }

    override fun visit(value: JsonObject) {
        // Validar as chaves
        for (key in value.entries.keys) {
            if (key.isBlank()) {
                isValid = false
                return
            }
        }
    }
}