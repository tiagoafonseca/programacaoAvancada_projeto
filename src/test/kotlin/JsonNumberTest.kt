import main.JsonNumber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonNumberTest {
    @Test
    fun testInteger() {
        val json = JsonNumber(42)
        assertEquals("42", json.toJsonString())
    }

    @Test
    fun testDouble() {
        val json = JsonNumber(3.14)
        assertEquals("3.14", json.toJsonString())
    }




}