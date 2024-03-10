package com.example.menu

import com.example.register.User
import com.example.serializer.Serializer
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import com.example.storage.Storage
import io.ktor.http.*
import io.ktor.server.response.*

fun Application.configureMenuRouting() {
    routing {
        post("/menu") {
            try {
                val data = call.receive<String>()
                Serializer.refresh()
                val person = Storage.userList.find { it.login == data }
                if (person != null && person.isLogged()) {
                    call.respond(HttpStatusCode.OK, Storage.menu.prettifyMenu())
                }
                call.respond(HttpStatusCode.Forbidden, "Sign up or log in to see our menu!")
            } catch (e: io.ktor.server.plugins.BadRequestException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong. Please make sure that you filled the form properly"
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
    }
}