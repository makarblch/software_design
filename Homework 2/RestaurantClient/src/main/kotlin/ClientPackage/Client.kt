package ClientPackage

import Storage.Request
import Storage.Role
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration


class Client {
    private val client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(30)).build()
    internal var isLogged = false
    internal var userType: Role = Role.Guest
    internal var login = ""

    init {
        val result = makeRequest("", Request.Get, "")
        if (result?.statusCode() != 200) {
            println("The server doesn't work")
        }
    }

    private fun makeRequest(path: String, req: Request, body: String): HttpResponse<String>? {
        return try {
            val request: HttpRequest = if (req == Request.Get) {
                HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/$path"))
                    .GET().build()
            } else {
                HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/$path"))
                    .POST(HttpRequest.BodyPublishers.ofString(body)).build()
            }
            val result = client.send(request, HttpResponse.BodyHandlers.ofString())
            result
        } catch (e: Exception) {
            null
        }
    }

    fun signUpGuest(name: String, login: String, password: String): String {
        val result = makeRequest("register/guest", Request.Post, Json.encodeToString(User(name, login, password)))
        return if (result != null) {
            this.login = login
            result.body()
        } else {
            "The server doesn't work"
        }
    }

    fun signUpAdmin(name: String, login: String, password: String): String {
        val result = makeRequest("register/admin", Request.Post, Json.encodeToString(User(name, login, password)))
        return if (result != null) {
            this.login = login
            userType = Role.Admin
            result.body()
        } else {
            "The server doesn't work"
        }
    }

    fun login(name: String, login: String, password: String): String {
        val result = makeRequest("login", Request.Post, Json.encodeToString(User(name, login, password)))
        return if (result != null) {
            this.login = login
            result.body()
        } else {
            "The server doesn't work"
        }
    }

    fun logout(): String {
        val result = makeRequest("logout", Request.Post, Json.encodeToString(login))
        return result?.body() ?: "The server doesn't work"
    }

    fun addDish(name: String, price: String, time: String, count: String = "1"): String {
        val result = makeRequest("add", Request.Post, Json.encodeToString(arrayOf(login, name, price, time, count)))
        return result?.body() ?: "The server doesn't work"
    }

    fun changeDish(parameters: Array<String>): String {
        val result = makeRequest("change", Request.Post, Json.encodeToString(parameters))
        return result?.body() ?: "The server doesn't work"
    }

    fun deleteDish(name: String): String {
        val result = makeRequest("delete", Request.Post, Json.encodeToString(arrayOf(login, name)))
        return result?.body() ?: "The server doesn't work"
    }

    fun getMenu(): String {
        val result =
            makeRequest("menu", Request.Post, login)
        return result?.body() ?: "The server doesn't work"
    }

    fun getPopularDish(): String {
        val result =
            makeRequest("stats/dish", Request.Post, login)
        return result?.body() ?: "The server doesn't work"
    }

    fun getAverageReview(): String {
        val result =
            makeRequest("stats/scores", Request.Post, login)
        return result?.body() ?: "The server doesn't work"
    }

    fun getPositiveReview(): String {
        val result =
            makeRequest("stats/positive", Request.Post, login)
        return result?.body() ?: "The server doesn't work"
    }


    fun makeOrder(parameters: Array<String>): String {
        val result =
            makeRequest("order/make", Request.Post, Json.encodeToString(arrayOf(login, *parameters)))
        return result?.body() ?: "The server doesn't work"
    }

    fun cancelOrder(orderId: String): String {
        val result =
            makeRequest("order/cancel", Request.Post, Json.encodeToString(arrayOf(login, orderId)))
        return result?.body() ?: "The server doesn't work"
    }

    fun getStatus(orderId: String): String {
        val result =
            makeRequest("order/status", Request.Post, Json.encodeToString(arrayOf(login, orderId)))
        return result?.body() ?: "The server doesn't work"
    }

    fun payOrder(orderId: String): String {
        val result =
            makeRequest("order/pay", Request.Post, Json.encodeToString(arrayOf(login, orderId)))
        return result?.body() ?: "The server doesn't work"
    }

    fun addDish(parameters: Array<String>): String {
        val result =
            makeRequest("order/add", Request.Post, Json.encodeToString(arrayOf(login, *parameters)))
        return result?.body() ?: "The server doesn't work"
    }

    fun deleteDish(parameters: Array<String>): String {
        val result =
            makeRequest("order/delete", Request.Post, Json.encodeToString(arrayOf(login, *parameters)))
        return result?.body() ?: "The server doesn't work"
    }

    fun addReview(order : String, rate : String, text : String) : String {
        val result =
            makeRequest("order/review", Request.Post, Json.encodeToString(arrayOf(login, order, rate, text)))
        return result?.body() ?: "The server doesn't work"
    }

    fun disconnect() {
        client.close()
    }

}