import main.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonValidateValidatorTest {

    @Test
    fun testValidKeys() {
        val json = JsonObject(
            mapOf("name" to JsonString("Ana"), "age" to JsonNumber(22))
        )

        val validator = JsonBaseValidator()
        json.isValidType(validator)

        assertTrue(validator.isValid)
    }

    @Test
    fun testInvalidKeyEmptyString() {
        val json = JsonObject(
            mapOf("" to JsonString("oops"))
        )

        val validator = JsonBaseValidator()
        json.isValidType(validator)

        assertFalse(validator.isValid)
    }

    @Test
    fun testInvalidKeySpacesOnly() {
        val json = JsonObject(
            mapOf("   " to JsonString("oops"))
        )

        val validator = JsonBaseValidator()
        json.isValidType(validator)

        assertFalse(validator.isValid)
    }

    @Test
    fun testHomogeneousArrayValid() {
        val json = JsonArray(
            listOf(JsonNumber(1), JsonNumber(2), JsonNumber(3))
        )

        val validator = JsonBaseValidator()
        json.isValidType(validator)

        assertTrue(validator.isValid)
    }

    @Test
    fun testHomogeneousArrayWithNulls() {
        val json = JsonArray(
            listOf(JsonString("a"), JsonNull, JsonString("b"))
        )

        val validator = JsonBaseValidator()
        json.isValidType(validator)

        assertTrue(validator.isValid)
    }

    @Test
    fun testHeterogeneousArrayInvalid() {
        val json = JsonArray(
            listOf(JsonNumber(1), JsonString("oops"), JsonNull)
        )

        val validator = JsonBaseValidator()
        json.isValidType(validator)

        assertFalse(validator.isValid)
    }
}