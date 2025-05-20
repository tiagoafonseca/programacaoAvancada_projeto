import main.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonObjectTest {
    @Test
    fun testEmptyObject() {
        val json = JsonObject(emptyMap())
        assertEquals("{}", json.toJsonString())
    }

    @Test
    fun testSimpleObject() {
        val json = JsonObject(
            mapOf(
                "name" to JsonString("Alice"),
                "age" to JsonNumber(30)
            )
        )
        assertEquals("{\"name\":\"Alice\",\"age\":30}", json.toJsonString())
    }

    @Test
    fun testObjectWithNull() {
        val json = JsonObject(
            mapOf(
                "value" to JsonNull
            )
        )
        assertEquals("{\"value\":null}", json.toJsonString())
    }

    @Test
    fun testFilterEmptyObject() {
        val obj = JsonObject(
            mapOf(
                "x" to JsonBoolean(false)
            )
        )

        val filtered = obj.filter { _, _ -> false }

        assertEquals("{}", filtered.toJsonString())
    }

    @Test
    fun testFilterObjectByValue() {
        val obj = JsonObject(
            mapOf(
                "a" to JsonNumber(1),
                "b" to JsonNumber(2),
                "c" to JsonNumber(3)
            )
        )

        val filtered = obj.filter { _, value ->
            value is JsonNumber && value.value.toInt() > 1
        }

        assertEquals("{\"b\":2,\"c\":3}", filtered.toJsonString())
    }

    @Test
    fun testFilterObjectByKey() {
        val obj = JsonObject(
            mapOf(
                "keep" to JsonString("yes"),
                "drop" to JsonString("no")
            )
        )

        val filtered = obj.filter { key, _ -> key == "keep" }

        assertEquals("{\"keep\":\"yes\"}", filtered.toJsonString())
    }
}