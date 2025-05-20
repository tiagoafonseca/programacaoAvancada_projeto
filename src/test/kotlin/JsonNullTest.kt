import main.JsonNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonNullTest {

    @Test
    fun testNull() {
        assertEquals("null", JsonNull.toJsonString())
    }
}