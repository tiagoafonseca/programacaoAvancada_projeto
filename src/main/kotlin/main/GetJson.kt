package main

import com.sun.net.httpserver.HttpServer
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.valueParameters
import java.net.InetSocketAddress

class GetJson(private val controllerClass: KClass<*>) {
    private val controllerInstance = controllerClass.constructors.first().call()

    fun start(port: Int) {
        val server = HttpServer.create(InetSocketAddress(port), 0)

        val basePath = controllerClass.findAnnotation<Mapping>()?.value ?: ""

        controllerClass.memberFunctions.forEach { func ->
            val mapping = func.findAnnotation<Mapping>() ?: return@forEach
            val path = "/$basePath/${mapping.value}".replace("//", "/")

            server.createContext(path) { exchange ->
                try {
                    val params = mutableListOf<Any?>()

                    func.valueParameters.forEach { param ->
                        val name = param.name
                        val annotations = param.annotations

                        val value = when {
                            annotations.any { it is Path } -> {
                                val regex = "\\{(\\w+)}".toRegex()
                                val match = regex.find(mapping.value)
                                val varName = match?.groups?.get(1)?.value
                                exchange.requestURI.path.split("/").last()
                            }
                            annotations.any { it is Param } -> {
                                val queryParams = parseQueryParams(exchange.requestURI.query)
                                queryParams[name]
                            }
                            else -> null
                        }

                        params.add(convertValue(param.type.classifier as KClass<*>, value))
                    }

                    val result = func.call(controllerInstance, *params.toTypedArray())
                    val json = result.toString()

                    exchange.sendResponseHeaders(200, json.toByteArray().size.toLong())
                    exchange.responseBody.use { it.write(json.toByteArray()) }

                } catch (e: Exception) {
                    e.printStackTrace()
                    exchange.sendResponseHeaders(500, 0)
                    exchange.responseBody.use { it.write("Internal Server Error".toByteArray()) }
                }
            }
        }

        server.executor = null
        server.start()
        println("Server started at http://localhost:$port")
    }

    private fun parseQueryParams(query: String?): Map<String, String> {
        if (query == null) return emptyMap()
        return query.split("&").mapNotNull {
            val (k, v) = it.split("=")
            if (k != null && v != null) k to v else null
        }.toMap()
    }

    private fun convertValue(type: KClass<*>, value: String?): Any? {
        if (value == null) return null
        return when (type) {
            Int::class -> value.toInt()
            Double::class -> value.toDouble()
            Boolean::class -> value.toBoolean()
            String::class -> value
            else -> value
        }
    }
}
