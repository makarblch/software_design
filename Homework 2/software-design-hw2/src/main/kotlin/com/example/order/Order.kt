package com.example.order

import com.example.kitchen.Kitchen
import com.example.kitchen.MAXIMAL
import com.example.logger.Logger
import com.example.menu.Dish
import com.example.review.Review
import com.example.serializer.Serializer
import kotlinx.serialization.Serializable


enum class OrderStatus {
    Created,
    Waiting,
    Preparing,
    Ready,
    Paid,
    No
}

var locker = Any()

@Serializable
class Order {
    @Serializable var id = 0
    @Serializable var dishes: MutableList<Dish> = mutableListOf()
    @Serializable var status: OrderStatus = OrderStatus.No
    @Serializable var review : Review = Review(0, "")
    @Serializable var person : String = ""

    fun cooking() {
        if (Kitchen.numOrders >= MAXIMAL) {
            status = OrderStatus.Waiting
            Kitchen.queue.add(this)
            Serializer.refresh()
        } else {
            ++Kitchen.numOrders
            val thread = Thread {
                status = OrderStatus.Preparing
                Logger.log("Status of order $id changed to $status")
                Thread.sleep(Kitchen.orderBuilder.getTime())
                status = OrderStatus.Ready
                Logger.log("Status of order $id changed to $status")
                --Kitchen.numOrders
                //  println(Kitchen.queue.size)
                if (Kitchen.queue.isNotEmpty()) {
                    synchronized(locker) {
                        val temp = Kitchen.queue.first()
                        Kitchen.queue.removeAt(0)
                        temp.cooking()
                    }
                }
                Serializer.refresh()
            }
            thread.start()
        }
    }

    fun getPrice() : Int {
        var summ = 0
        for (element in dishes) {
            summ += element.price
        }
        return summ
    }
}