package com.example.statistics

import com.example.manager.UtilityManager
import com.example.menu.Dish
import com.example.order.OperationStatus
import com.example.serializer.Serializer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureStatisticsRouting() {
    routing {
        post("stats/dish") {
            try {
                val data = call.receive<String>()
                val res = UtilityManager.popularDish(data)
                if (res is String) {
                    call.respond(HttpStatusCode.OK, res)
                } else if ((res as OperationStatus) == OperationStatus.Forbidden) {
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights!")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Something went wrong :(")
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
        post("stats/scores") {
            try {
                val data = call.receive<String>()
                val res = UtilityManager.averageScore(data)
                if (res is Double) {
                    call.respond(HttpStatusCode.OK, res)
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights")
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something went wrong :( Please, try again."
                )
            }
        }
        post("stats/positive") {
            try {
                val data = call.receive<String>()
                val res = UtilityManager.percentOrdersWithPositiveRates(data)
                if (res is Double) {
                    call.respond(HttpStatusCode.OK, res)
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Not enough rights")
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