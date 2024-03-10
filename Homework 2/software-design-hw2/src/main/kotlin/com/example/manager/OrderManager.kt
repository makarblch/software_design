package com.example.manager

import com.example.kitchen.Kitchen
import com.example.logger.Logger
import com.example.menu.Dish
import com.example.order.*
import com.example.plugins.checkerAdmin
import com.example.plugins.checkerGuest
import com.example.serializer.Serializer
import com.example.storage.Storage
import com.example.storage.Storage.ID


object OrderManager {
    fun getOrderStatus(data: Array<String>): Any {
        return if (checkerGuest(data) || checkerAdmin(data)) {
            val order = Storage.orderList.find { it.id == data[1].toInt() }
            if (order != null && order.person == data[0]) {
                Logger.log("User ${data[0]} checked the status of their order")
//                for (el in Storage.orderList) {
//                    println("${el.id}, ${el.status}")
//                }
                Serializer.refresh()
                order.status
            } else {
                Logger.log("User ${data[0]} checked non-existent order")
                "This order doesn't exist"
            }
        } else {
            Logger.log("User ${data[0]} failed to check the status of their order")
            "No user found"
        }
    }

    fun payOrder(data: Array<String>): OperationStatus {
        if (checkerGuest(data) || checkerAdmin(data)) {
            val order = Storage.orderList.find { it.id == data[1].toInt() }
            if (order != null && order.person == data[0]) {
                order.status = OrderStatus.Paid
                Logger.log("User ${data[0]} payed their order")
                Storage.income += order.getPrice()
                Serializer.refresh()
                return OperationStatus.Success
            } else {
                Logger.log("User ${data[0]} failed to pay their order as it wasn't found")
                return OperationStatus.Fail
            }
        } else {
            Logger.log("No user ${data[0]} found to pay the cheque")
            return OperationStatus.Forbidden
        }
    }

    fun deleteOrder(data: Array<String>): OperationStatus {
        if (checkerGuest(data) || checkerAdmin(data)) {
            val order = Storage.orderList.find { it.id == data[1].toInt() }
            return if (order != null && order.person == data[0] && order.status != OrderStatus.Ready && order.status != OrderStatus.Paid) {
                Kitchen.orderBuilder.order = order
                for (dish in data.slice(2..<data.size - 1)) {
                    Kitchen.orderBuilder.deleteDish(dish)
                }
                Logger.log("User ${data[0]} deleted dishes from their order")
                Serializer.refresh()
                OperationStatus.Success
            } else {
                Logger.log("User ${data[0]} tried to delete dishes from non-existent order")
                OperationStatus.Fail
            }
        } else {
            Logger.log("User ${data[0]} failed to delete dishes")
            return OperationStatus.Forbidden
        }
    }

    fun addOrder(data: Array<String>): OperationStatus {
        if (checkerGuest(data) || checkerAdmin(data)) {
            val order = Storage.orderList.find { it.id == data[1].toInt() }
            return if (order != null && order.person == data[0] && order.status != OrderStatus.Ready && order.status != OrderStatus.Paid) {
                Kitchen.orderBuilder.order = order
                for (dish in data.slice(2..<data.size)) {
                    Kitchen.orderBuilder.addDish(dish)
                }
                Logger.log("User ${data[0]} added dishes to their order")
                Serializer.refresh()
                OperationStatus.Success
            } else {
                Logger.log("User ${data[0]} tried to add dishes to non-existent order")
                OperationStatus.Fail
            }
        } else {
            Logger.log("User ${data[0]} failed to add dishes")
            return OperationStatus.Forbidden
        }
    }

    fun cancelOrder(data: Array<String>): OperationStatus {
        return if (checkerGuest(data) || checkerAdmin(data)) {
            val order = Storage.orderList.find { it.id == data[1].toInt() }
            if (order != null && order.status != OrderStatus.Ready && order.status != OrderStatus.Paid && order.person == data[0]) {
                Logger.log("User ${data[0]} cancelled their order")
                Serializer.refresh()
                OperationStatus.Success
            } else {
                Logger.log("User ${data[0]} tried to delete dishes from non-existent order")
                OperationStatus.Fail
            }
        } else {
            Logger.log("User ${data[0]} failed to delete dishes")
            OperationStatus.Forbidden
        }
    }

    fun makeOrder(data: Array<String>): Any {
        val person = Storage.userList.find { it.login == data[0] }
        if (person != null && person.isLogged()) {
            val order = Order()
            synchronized(lock) {
                order.person = person.login
                Kitchen.orderBuilder.order = order
                for (dish in data.slice(1..<data.size)) {
                    Kitchen.orderBuilder.addDish(dish)
                }
                Kitchen.orderBuilder.order.id = ID
                ++ID
                Kitchen.orderBuilder.setStatus(OrderStatus.Created)
            }
            val temp = Storage.menu.checkDishesAbsence(order.dishes)
            return if (!temp) {
                Storage.orderList.add(order)
                Logger.log("User ${data[0]} created an ${order.id} order")
                order.cooking()
                order.id
            } else {
                Logger.log("User ${data[0]} failed to create an ${order.id} order (no dishes available)")
                OperationStatus.Fail
            }
        } else {
            Logger.log("User ${data[0]} failed to create order")
            return OperationStatus.Fail
        }
    }

    fun addReview(data: Array<String>): OperationStatus {
        Serializer.refresh()
        if (checkerGuest(data) || checkerAdmin(data)) {
            val order = Storage.orderList.find { it.id == data[1].toInt() }
            if (order != null && order.review.rate == 0 && order.person == data[0]) {
                if (data[2].toInt() in 0..5) {
                    order.review.rate = data[2].toInt()
                    order.review.comment = data[3]
                    Storage.reviewList.add(order.review)
                    Logger.log("User ${data[0]} added review to their order")
                    return OperationStatus.Success
                } else {
                    Logger.log("User ${data[0]} added incorrect rate to review of their order")
                    return OperationStatus.Fail
                }
            } else {
                Logger.log("User ${data[0]} failed to add review to their order as it weren't found")
                return OperationStatus.Fail
            }
        } else {
            Logger.log("No user ${data[0]} found to pay the cheque")
            return OperationStatus.Forbidden
        }
    }
}