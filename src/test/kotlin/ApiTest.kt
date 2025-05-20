import main.JsonObject
import main.fromKotlinObject
import okhttp3.*
import kotlin.test.Test
import kotlin.test.assertEquals
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class ApiTest {

    private val client = OkHttpClient()

    @Test
    fun testInts() {
        val req = Request.Builder().url("http://localhost:8080/api/ints").build()
        val res = client.newCall(req).execute()
        assertEquals("[1, 2, 3]", res.body?.string())
    }

    @Test
    fun testArgs() {
        val req = Request.Builder().url("http://localhost:8080/api/args?n=2&text=ha").build()
        val res = client.newCall(req).execute()
        assertEquals("{ha=haha}", res.body?.string())
    }

    @Test
    fun testInference() {
        val req = Request.Builder().url("http://localhost:8080/api/toJson?obj=%7B%22name%22%3A%22Diogo%22%2C%22age%22%3A22%7D").build()
        val res = client.newCall(req).execute()
        assertEquals("{\"name\":\"Diogo\",\"age\":22}", res.body?.string())
    }

    @Test
    fun testTransofrm() {
        val req = Request.Builder().url("http://localhost:8080/api/transform$").build()
        val res = client.newCall(req).execute()
        assertEquals("{\"name\":\"Diogo\",\"age\":22}", res.body?.string())
    }

    @Test
    fun testFilter() {
        val req = Request.Builder().url("http://localhost:8080/api/filter").build()
        val res = client.newCall(req).execute()
        assertEquals("{\"name\":\"Diogo\",\"age\":22}", res.body?.string())
    }
}