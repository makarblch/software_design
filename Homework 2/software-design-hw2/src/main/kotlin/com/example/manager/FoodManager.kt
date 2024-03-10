package com.example.manager

import com.example.logger.Logger
import com.example.order.OperationStatus
import com.example.plugins.checkerAdmin
import com.example.serializer.Serializer
import com.example.storage.Storage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

object FoodManager {
    fun addDish(data : Array<String>) : OperationStatus {
        return if (checkerAdmin(data)) {
            val res = Storage.menu.addDish(Storage.menu.getNumberOfDishes() + 1,data[1], data[2].toInt(), data[3].toInt(), data[4].toInt())
            if (res == OperationStatus.Success) {
                Logger.log("Admin ${data[0]} added new ${data[0]} dish")
                Serializer.refresh()
                OperationStatus.Success
            } else {
                Logger.log("Admin ${data[0]} failed to add new ${data[0]} dish")
                Serializer.refresh()
                OperationStatus.Fail
            }
        } else {
            Logger.log("User ${data[0]} failed to add ${data[0]} dish")
            OperationStatus.Forbidden
        }
    }

    fun changeDish(data : Array<String>) : OperationStatus {
        Serializer.refresh()
        return if (checkerAdmin(data)) {
            val dish = Storage.menu.findDishName(data[1])
            println(dish?.name)
            if (dish != null) {
                Storage.menu.changeDish(data[1], data)
                Logger.log("Admin ${data[0]} changed dish to ${data[0]}")
                Serializer.refresh()
                OperationStatus.Success
            } else {
                Logger.log("Admin ${data[0]} failed to change dish to ${data[0]}")
                OperationStatus.Fail
            }
        } else {
            Logger.log("User ${data[0]} failed to change dish to ${data[0]}")
            OperationStatus.Forbidden
        }
    }

    fun deleteDish(data : Array<String>) : OperationStatus {
        Serializer.refresh()
        return if (checkerAdmin(data)) {
            if (Storage.menu.dishes.keys.find { it.name == data[1] } != null) {
                Storage.menu.deleteDish(data[1])
                Logger.log("Admin ${data[0]} deleted ${data[0]} dish")
                Serializer.refresh()
                OperationStatus.Success
            } else {
                Logger.log("Admin ${data[0]} failed to delete ${data[0]} dish")
                OperationStatus.Fail
            }
        } else {
            Logger.log("User ${data[0]} failed to delete ${data[0]} dish")
            OperationStatus.Forbidden
        }
    }
}