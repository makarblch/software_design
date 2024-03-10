package com.example.manager

import com.example.logger.Logger
import com.example.menu.Dish
import com.example.order.OperationStatus
import com.example.plugins.checkerAdmin
import com.example.serializer.Serializer
import com.example.storage.Storage

object UtilityManager {
    fun popularDish(login : String) : Any {
        if (checkerAdmin(login)) {
            val foodCounts = mutableMapOf<Dish, Int>()

            for (order in Storage.orderList) {
                for (dish in order.dishes) {
                    foodCounts[dish] = (foodCounts[dish] ?: 0) + 1
                }
            }
            val mostFrequentDish = foodCounts.maxByOrNull { it.value }?.key
            if (mostFrequentDish != null) {
                Serializer.refresh()
                Logger.log("Admin $login got statistics of popular dishes")
                return mostFrequentDish.name
            }
            Logger.log("Admin $login got statistics of popular dishes")
            Serializer.refresh()
            return OperationStatus.Fail
        } else {
            Logger.log("User $login failed to get statistics of popular dishes")
            return OperationStatus.Forbidden
        }
    }

    fun averageScore(login : String) : Any {
        if (checkerAdmin(login)) {
            var summ = 0
            var count = 0
            for (element in Storage.orderList) {
                summ += element.review.rate
                if (element.review.rate != 0) {
                    ++count
                }
            }
            Logger.log("Admin $login got average scores of order reviews")
            if (Storage.orderList.isNotEmpty()) {
                Serializer.refresh()
                return summ.toDouble() / count
            }
            Serializer.refresh()
            return 0.0
        } else {
            Logger.log("User $login failed to get average scores of order reviews")
            return OperationStatus.Forbidden
        }
    }

    fun percentOrdersWithPositiveRates(login : String) : Any {
        Serializer.refresh()
        if (checkerAdmin(login)) {
            var count = 0
            for (element in Storage.orderList) {
                if (element.review.rate >= 3) {
                    ++count
                }
            }
            Logger.log("Admin $login got positive scores of order reviews")
            if (Storage.orderList.isNotEmpty()) {
                return count.toDouble() / Storage.orderList.size
            }
            return 0.0
        } else {
            Logger.log("User $login failed to get positive scores of order reviews")
            return OperationStatus.Forbidden
        }
    }

}