# 📦 JSON Library – Kotlin

Projeto para a unidade curricular **Advanced Programming – 2024/2025**

Biblioteca desenvolvida em Kotlin para modelação, manipulação e inferrência de estruturas JSON. Não utiliza bibliotecas externas de JSON – tudo é implementado de raiz.

## ❗️ Erro ao importar o projeto:
- Caso esteja este erro: **“Failed to download Kotlin Compiler Maven artifact (org.jetbrains.kotlin:kotlin-dist-for-jps-meta:2.1.0).”**
- Solução:
  - Vai a:
    - ```File > Settings > Build, Execution, Deployment > Compiler > Kotlin Compiler```
  - Em Kotlin Compiler Version mude a versão que lá esteja para:
    - ```Bundled (IDE default)```

    
---

## ✅ Funcionalidades

### 📌 Fase 1 – JSON Model

- Modelação de todos os tipos JSON:
  - `JsonString`
  - `JsonNumber`
  - `JsonBoolean`
  - `JsonNull`
  - `JsonArray`
  - `JsonObject`
- Serialização para strings JSON válidas com `toJsonString()`
- Operações funcionais:
  - `filter()` em `JsonArray` e `JsonObject`
  - `map()` em `JsonArray`
- Padrão de design **Visitor** para percorrer estruturas
- Validações com visitor:
  - Chaves válidas (não vazias, nem espaços)
  - Arrays com tipos homogéneos (ignora `null`)
- Testes unitários organizados por tipo

---

### 📌 Fase 2 – Inference com Reflection

- Conversão automática de objetos Kotlin para JSON com:

```kotlin
fun fromKotlinObject(obj: Any?): JsonValue
```

- Suporte a:
```kotlin
- Int, Double, Boolean, String
- Enum
- List<*>
- Map<String, *>
- data class
- null
```
- Uso de ```kotlin kotlin.reflect.full.memberProperties``` para analisar ```kotlin data class```
- Testes para todos os tipos inferidos

## Serialização JSON
```kotlin
val json = JsonObject(
    mapOf(
        "name" to JsonString("Ana"),
        "age" to JsonNumber(25),
        "active" to JsonBoolean(true)
    )
)

println(json.toJsonString())
// {"name":"Ana","age":25,"active":true}
```

## 🔄 Operações funcionais
### JsonArray.map
``` kotlin
val array = JsonArray(listOf(JsonNumber(1), JsonNumber(2)))
val doubled = array.map { JsonNumber((it as JsonNumber).value.toInt() * 2) }
// Resultado: [2, 4]
```

### JsonObject.filter
``` kotlin
val obj = JsonObject(
    mapOf("a" to JsonNumber(1), "b" to JsonNumber(2))
)
val filtered = obj.filter { key, value -> (value as JsonNumber).value.toInt() > 1 }
// Resultado: {"b":2}
```

## 🕵️ Validação com Visitor
Permite percorrer a estrutura e validar:

- Se todas as chaves dos objetos são válidas
- Se todos os arrays contêm elementos do mesmo tipo
``` kotlin
val validator = JsonValidatorVisitor()
json.accept(validator)

println("JSON válido? ${validator.isValid}")
```

## 🤖 Inference: fromKotlinObject
### Exemplo com data class
``` kotlin
data class Person(val name: String, val age: Int)

val person = Person("Ana", 30)
val json = fromKotlinObject(person)

println(json.toJsonString())
// {"name":"Ana","age":30}
```
### Exemplo com estrutura complexa
``` kotlin
enum class Role { ADMIN, USER }

val data = mapOf(
    "user" to Person("Ana", 30),
    "roles" to listOf(Role.ADMIN, Role.USER),
    "active" to true
)

val json = fromKotlinObject(data)

println(json.toJsonString())
// {"user":{"name":"Ana","age":30},"roles":["ADMIN","USER"],"active":true}
```

## 🧪 Testes
- Cobrem todos os tipos JSON e funcionalidades (filter, map, toJsonString)
- Testes de inferência com List, Map, data class, enum
- Organizados por ficheiro (JsonArrayTest.kt, JsonObjectTest.kt, InferenceTest.kt, ...)
- Pode correr os testes execuntando o seguinte comando: ``` mvn clean tests```

## 🧰 Requisitos
Adicionar ao ```pom.xml```:
```
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-reflect</artifactId>
    <version>${current-kotlin-version}</version>
</dependency>
```

## 📄 Elaborado por:
- Tiago Afonseca
- Diogo Juncal
