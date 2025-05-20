import main.JsonBoolean
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonBooleanTest {
    @Test
    fun testBoolean() {
        val jsonTrue = JsonBoolean(true)
        assertEquals("true", jsonTrue.toJsonString())
        val jsonFalse = JsonBoolean(false)
        assertEquals("false", jsonFalse.toJsonString())
    }
}