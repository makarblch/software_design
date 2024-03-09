package com.example.review

import com.example.manager.OrderManager
import com.example.order.OperationStatus
import com.example.order.OrderStatus
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json


fun Application.configureReviewRouting() {
    routing {
        post("order/review") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<Array<String>>(data1)
                val res = OrderManager.addReview(data)
                if (res == OperationStatus.Success) {
                    call.respond(HttpStatusCode.OK, "Successfully added!")
                } else if (res == OperationStatus.Fail) {
                    call.respond(HttpStatusCode.BadRequest, "Failed to add review due to incorrect rate, order number or review already exists")
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Failed to add review as user wasn't found")
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
    }
}