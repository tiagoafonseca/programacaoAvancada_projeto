import main.JsonArray
import main.JsonBoolean
import main.JsonNumber
import main.JsonString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonArrayTest {
    // Testes de serialização
    @Test
    fun testEmptyArraySerialization() {
        val json = JsonArray(emptyList())
        assertEquals("[]", json.toJsonString())
    }

    @Test
    fun testMixedArraySerialization() {
        val json = JsonArray(
            listOf(JsonString("a"), JsonNumber(1), JsonBoolean(true))
        )
        assertEquals("[\"a\",1,true]", json.toJsonString())
    }

    // Testes de map
    @Test
    fun testMapToString() {
        val array = JsonArray(
            listOf(JsonBoolean(true), JsonBoolean(false))
        )

        val mapped = array.map {
            JsonString(it.toJsonString())
        }

        assertEquals("[\"true\",\"false\"]", mapped.toJsonString())
    }

    @Test
    fun testMapNumbersDouble() {
        val array = JsonArray(
            listOf(JsonNumber(1), JsonNumber(2), JsonNumber(3))
        )

        val mapped = array.map {
            if (it is JsonNumber) JsonNumber(it.value.toInt() * 2) else it
        }

        assertEquals("[2,4,6]", mapped.toJsonString())
    }

    // Testes de filter
    @Test
    fun testFilterOnlyNumbers() {
        val array = JsonArray(
            listOf(JsonNumber(1), JsonString("a"), JsonNumber(2))
        )

        val filtered = array.filter { it is JsonNumber }
        assertEquals("[1,2]", filtered.toJsonString())
    }

    @Test
    fun testFilterBooleans() {
        val array = JsonArray(
            listOf(
                JsonBoolean(true),
                JsonBoolean(false),
                JsonBoolean(true)
            )
        )

        val filtered = array.filter { it is JsonBoolean && it.value }
        assertEquals("[true,true]", filtered.toJsonString())
    }

    @Test
    fun testFilterNoMatch() {
        val array = JsonArray(
            listOf(JsonString("x"), JsonString("y"))
        )

        val filtered = array.filter { it is JsonNumber }
        assertEquals("[]", filtered.toJsonString())
    }

    @Test
    fun testFilterNumbersAboveThreshold() {
        val array = JsonArray(
            listOf(
                JsonNumber(1),
                JsonNumber(2),
                JsonNumber(3),
                JsonString("not a number")
            )
        )
        val filtered = array.filter {
            it is JsonNumber && it.value.toDouble() > 1.5
        }
        assertEquals("[2,3]", filtered.toJsonString())
    }

    @Test
    fun testFilterEmptyResult() {
        val array = JsonArray(
            listOf(JsonNumber(1), JsonNumber(2))
        )

        val filtered = array.filter { it is JsonString }
        assertEquals("[]", filtered.toJsonString())
    }
}