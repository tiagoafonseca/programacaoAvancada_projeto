import main.fromKotlinObject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class InferenceTest {

    enum class Role { ADMIN, USER }
    data class Person(val name: String, val age: Int, val active: Boolean)

    @Test
    fun testSimpleTypes() {
        assertEquals("42", fromKotlinObject(42).toJsonString())
        assertEquals("3.14", fromKotlinObject(3.14).toJsonString())
        assertEquals("\"hello\"", fromKotlinObject("hello").toJsonString())
        assertEquals("true", fromKotlinObject(true).toJsonString())
        assertEquals("null", fromKotlinObject(null).toJsonString())
    }

    @Test
    fun testEnum() {
        assertEquals("\"ADMIN\"", fromKotlinObject(Role.ADMIN).toJsonString())
    }

    @Test
    fun testList() {
        val list = listOf("a", "b", "c")
        assertEquals("[\"a\",\"b\",\"c\"]", fromKotlinObject(list).toJsonString())
    }

    @Test
    fun testMap() {
        val map = mapOf("x" to 1, "y" to true)
        assertEquals("{\"x\":1,\"y\":true}", fromKotlinObject(map).toJsonString())
    }

    @Test
    fun testDataClass() {
        val p = Person("Ana", 25, true)
        val json = fromKotlinObject(p)
        assertEquals("{\"active\":true,\"age\":25,\"name\":\"Ana\"}", json.toJsonString())
    }

    @Test
    fun testNestedStructure() {
        val complex = mapOf(
            "user" to Person("Ana", 25, true),
            "roles" to listOf(Role.ADMIN, Role.USER),
            "verified" to true
        )

        val json = fromKotlinObject(complex)
        val expected = "{\"user\":{\"active\":true,\"age\":25,\"name\":\"Ana\"},\"roles\":[\"ADMIN\",\"USER\"],\"verified\":true}"
        assertEquals(expected, json.toJsonString())
    }
}
