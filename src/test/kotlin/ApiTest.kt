import okhttp3.*
import kotlin.test.Test
import kotlin.test.assertEquals
import okhttp3.OkHttpClient
import okhttp3.Request

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
}