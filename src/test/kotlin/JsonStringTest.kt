import main.JsonString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonStringTest {
    @Test
    fun testString() {
        val json = JsonString("hello")
        assertEquals("\"hello\"", json.toJsonString())
    }
}