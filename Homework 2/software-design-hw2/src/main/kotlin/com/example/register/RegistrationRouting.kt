package com.example.register

import com.example.manager.UserManager
import com.example.order.OperationStatus
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import java.security.MessageDigest

/**
 * A private function to get hash of the password
 */
private fun getHash(inByte: ByteArray): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(inByte)
    return with(StringBuilder()) {
        bytes.forEach { byte -> append(String.format("%02X", byte)) }
        toString().lowercase()
    }
}

/**
 * A function to encrypt the password
 */
fun encryptPassword(password: String): String {
    return getHash(password.toByteArray())
}

fun Application.configureRegistration() {
    routing {
        post("/register/{role}") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<User>(data1)
                val res = UserManager.addAdmin(data)
                if (call.parameters["role"] == "admin" &&  res == OperationStatus.Success) {
                    call.respond(HttpStatusCode.OK, "Successfully signed up as admin!")
                } else if (call.parameters["role"] == "guest" && res == OperationStatus.Success) {
                    call.respond(HttpStatusCode.OK, "Successfully signed up as guest!")
                } else if (res == OperationStatus.Fail) {
                    call.respond(HttpStatusCode.BadRequest, "Such user already exists, try again!")
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Failed to sign up!")
                }
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

