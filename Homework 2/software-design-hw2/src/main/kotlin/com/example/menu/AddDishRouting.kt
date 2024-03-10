package com.example.menu

import com.example.logger.Logger
import com.example.manager.FoodManager
import com.example.order.OperationStatus
import com.example.plugins.checkerAdmin
import com.example.register.Admin
import com.example.storage.Storage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.storage.Storage.menu
import kotlinx.serialization.json.Json

fun Application.configureAddDishRouting() {
    routing {
        post("/add") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<Array<String>>(data1)
                val result = FoodManager.addDish(data)
                if (result == OperationStatus.Success) {
                    call.respond(HttpStatusCode.OK, "Successfully added!")
                } else if (result == OperationStatus.Forbidden){
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights!")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Failed to add")
                }
            } catch (e : TypeCastException) {
                call.respond(HttpStatusCode.BadRequest, "Incorrect parameters, try again!")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Incorrect parameters, try again!")
            }

        }
    }
}
