package com.example.login

import com.example.manager.UserManager
import com.example.order.OperationStatus
import com.example.register.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<User>(data1)
                val res = UserManager.logIn(data)
                if (res == OperationStatus.Success)  {
                    call.respond(HttpStatusCode.OK, "Logged in successfully!")
                } else if (res == OperationStatus.Fail) {
                    call.respond(HttpStatusCode.Forbidden, "No such user found!")
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Failed to log in!")
                }
            } catch (e: io.ktor.server.plugins.BadRequestException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong. Please make sure that you filled in your data properly"
                )
            } catch (e : Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
        post("/logout") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<String>(data1)
                val res = UserManager.logOut(data)
                if (res == OperationStatus.Success)  {
                    call.respond(HttpStatusCode.OK, "Logged out successfully!")
                } else if (res == OperationStatus.Forbidden) {
                    call.respond(HttpStatusCode.Forbidden, "No such user found")
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Failed to log in!")
                }
            } catch (e: io.ktor.server.plugins.BadRequestException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong. Please make sure that you filled in your data properly"
                )
            } catch (e : Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
    }
}