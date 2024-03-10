package com.example.serializer

import com.example.kitchen.Kitchen
import com.example.manager.OrderManager
import com.example.menu.Dish
import com.example.order.Order
import com.example.register.User
import com.example.review.Review
import com.example.storage.Storage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

object Serializer {
    var menuFile = File("menu.json")
    var orderFile = File("orders.json")
    var userFile = File("users.json")
    var incomeFile = File("income.json")
    var reviewFile = File("reviews.json")
    var numFile = File("numbers.json")

    init {
        try {
            menuFile.createNewFile()
            orderFile.createNewFile()
            userFile.createNewFile()
            incomeFile.createNewFile()
            reviewFile.createNewFile()
            numFile.createNewFile()
        } catch (e: IOException) {
            println("Unable to create files :(")
        } catch (_: Exception) {
            println("Ooops! Something went wrong")
        }
    }

    private val json = Json { allowStructuredMapKeys = true }

    fun serialize() {
        try {
            if (Storage.menu.dishes.isNotEmpty()) {
                menuFile.writeText(json.encodeToString<MutableMap<Dish, Int>>(Storage.menu.dishes))
            } else {
                menuFile.writeText("")
            }
            if (Storage.orderList.isNotEmpty()) {
                orderFile.writeText(json.encodeToString<MutableList<Order>>(Storage.orderList))
            } else {
                orderFile.writeText("")
            }
            if (Storage.userList.isNotEmpty()) {
                userFile.writeText(json.encodeToString<MutableList<User>>(Storage.userList))
            } else {
                userFile.writeText("")
            }
            if (Storage.reviewList.isNotEmpty()) {
                reviewFile.writeText(json.encodeToString<MutableList<Review>>(Storage.reviewList))
            } else {
                reviewFile.writeText("")
            }
            incomeFile.writeText(json.encodeToString<Int>(Storage.income))
            numFile.writeText(json.encodeToString<Int>(Storage.ID))
        } catch (ex : IOException) {
            println("Unable to write files :(")
        } catch (e : Exception) {
            println(e.message)
            println("Ooops! Something went wrong!")
        }
    }

    fun deserialize() {
        try {
            var read = File("menu.json").readText(Charsets.UTF_8)
            if (read != "") {
                Storage.menu.dishes = json.decodeFromString<MutableMap<Dish, Int>>(read)
            }
            read = File("orders.json").readText(Charsets.UTF_8)
            if (read != "") {
                Storage.orderList = json.decodeFromString<MutableList<Order>>(read)
            }
            read = File("users.json").readText(Charsets.UTF_8)
            if (read != "") {
                Storage.userList = json.decodeFromString<MutableList<User>>(read)
            }
            read = File("reviews.json").readText(Charsets.UTF_8)
            if (read != "") {
                Storage.reviewList = json.decodeFromString<MutableList<Review>>(read)
            }
            read = File("income.json").readText(Charsets.UTF_8)
            if (read != "") {
                Storage.income = json.decodeFromString<Int>(read)
            }
            read = File("numbers.json").readText(Charsets.UTF_8)
            if (read != "") {
                Storage.ID = json.decodeFromString<Int>(read)
            }
        } catch (ex : IOException) {
            println("Unable to read files :(")
        } catch (_ : Exception) {
            println("Ooops! Something went wrong!")
        }
    }

    fun refresh() {
        if (Kitchen.numOrders == 0) {
            serialize()
            deserialize()
        }
    }
}