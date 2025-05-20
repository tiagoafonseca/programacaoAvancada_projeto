package main


@Mapping("api")
class Controller {

    @Mapping("ints")
    fun demo(): List<Int> = listOf(1, 2, 3)

    @Mapping("pair")
    fun obj(): Pair<String, String> = Pair("um", "dois")

    @Mapping("path/{pathvar}")
    fun path(@Path pathvar: String): String = "$pathvar!"

    @Mapping("args")
    fun args(@Param n: Int, @Param text: String): Map<String, String> =
        mapOf(text to text.repeat(n))

    @Mapping("toJson")
    fun inference(@Param obj: Any?): String = fromKotlinObject(obj).toJsonString()

    @Mapping("transform")
    fun trans(@Param array: JsonArray): String =
        array.map {
            if (it is JsonString) it.value.uppercase() else it
        }.toJsonString()

    @Mapping("filter")
    fun filter(@Param above: Int, @Param array: JsonArray): String = array.filter {
        it is JsonNumber && it.value.toInt() > above
    }.toString()





}


