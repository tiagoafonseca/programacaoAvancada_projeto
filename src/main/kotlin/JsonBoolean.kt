/**
 * Represents a JSON boolean value (true or false).
 */
data class JsonBoolean(val value: Boolean) : JsonValue {
    override fun toJsonString(): String = value.toString()

    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }

}