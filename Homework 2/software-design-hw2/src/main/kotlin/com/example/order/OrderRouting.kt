package com.example.order

import com.example.kitchen.Kitchen
import com.example.logger.Logger
import com.example.manager.OrderManager
import com.example.plugins.checkerAdmin
import com.example.plugins.checkerGuest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import com.example.storage.Storage
import io.ktor.http.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json

val lock = Any()

fun Application.configureOrder() {
    routing {
        post("/order/status") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<Array<String>>(data1)
                val res = OrderManager.getOrderStatus(data)
                if (res is OrderStatus) {
                    call.respond(HttpStatusCode.OK, res)
                } else {
                    call.respond(HttpStatusCode.Forbidden, res)
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
        post("/order/pay") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<Array<String>>(data1)
                val res = OrderManager.payOrder(data)
                if (res == OperationStatus.Success) {
                    call.respond(HttpStatusCode.OK, "Successfully paid!")
                } else if (res == OperationStatus.Forbidden) {
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Failed to pay the order")
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
        post("/order/delete") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<Array<String>>(data1)
                val res = OrderManager.deleteOrder(data)
                if (res == OperationStatus.Success) {
                    call.respond(HttpStatusCode.OK, "Successfully deleted!")
                } else if (res == OperationStatus.Forbidden) {
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Failed to delete the dishes")
                }
            } catch (e: io.ktor.server.plugins.BadRequestException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong. Please make sure that you filled in properly"
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
        post("/order/add") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<Array<String>>(data1)
                val res = OrderManager.addOrder(data)
                if (res == OperationStatus.Success) {
                    call.respond(HttpStatusCode.OK, "Successfully added!")
                } else if (res == OperationStatus.Forbidden) {
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Failed to delete the dishes")
                }
            } catch (e: io.ktor.server.plugins.BadRequestException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong. Please make sure that you filled in properly"
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
        post("order/cancel") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<Array<String>>(data1)
                val res = OrderManager.cancelOrder(data)
                if (res == OperationStatus.Success) {
                    call.respond(HttpStatusCode.OK, "Successfully cancelled!")
                } else if (res == OperationStatus.Forbidden) {
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Failed to delete the dishes")
                }
            } catch (e: io.ktor.server.plugins.BadRequestException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong. Please make sure that you filled in properly"
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
        post("/order/make") {
            try {
                val data1 = call.receive<String>()
                val data = Json.decodeFromString<Array<String>>(data1)
                val res = OrderManager.makeOrder(data)
                if (res is Int) {
                    call.respond(HttpStatusCode.OK, "Your order number is $res")
                } else if (res == OperationStatus.Forbidden) {
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Failed to create order")
                }
            } catch (e: io.ktor.server.plugins.BadRequestException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong. Please make sure that you filled in properly"
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