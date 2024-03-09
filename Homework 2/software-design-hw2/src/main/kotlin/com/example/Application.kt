package com.example

import com.example.login.configureLoginRouting
import com.example.menu.configureAddDishRouting
import com.example.menu.configureChangeDishRouting
import com.example.menu.configureDeleteDishRouting
import com.example.menu.configureMenuRouting
import com.example.order.configureOrder
import com.example.plugins.*
import com.example.register.configureRegistration
import com.example.review.configureReviewRouting
import com.example.serializer.Serializer
import com.example.statistics.configureStatisticsRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    Serializer.deserialize()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureRegistration()
    configureMenuRouting()
    configureAddDishRouting()
    configureDeleteDishRouting()
    configureChangeDishRouting()
    configureLoginRouting()
    configureOrder()
    configureReviewRouting()
    configureStatisticsRouting()
}
