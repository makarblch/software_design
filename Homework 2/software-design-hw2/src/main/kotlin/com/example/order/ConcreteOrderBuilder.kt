package com.example.order

import com.example.menu.Dish
import com.example.storage.Storage

enum class OperationStatus {
    Success,
    Fail,
    Forbidden
}

val locker1 = Any()

class ConcreteOrderBuilder : OrderBuilder {
    var order = Order()

    override fun addDish(dish: String) : OperationStatus {
        val dish : Dish? = Storage.menu.findDish(dish)
        if (dish != null) {
            order.dishes.add(dish)
            return OperationStatus.Success
        }
        return OperationStatus.Fail
    }

    override fun deleteDish(dish: String) : OperationStatus {
        try {
            val dish : Dish? = order.dishes.find { it.ordNumber == dish.toInt() }
            if (dish != null) {
                order.dishes.remove(dish)
                return OperationStatus.Success
            }
            return OperationStatus.Fail
        } catch (e: Exception) {
           return OperationStatus.Fail
        }
    }

    override fun cancel(): OperationStatus {
        return try {
            val thread = Thread.currentThread()
            thread.interrupt()
            OperationStatus.Success
        } catch (e : Exception) {
            OperationStatus.Fail
        }
    }

    override fun payCheque() : OperationStatus {
        order.status = OrderStatus.Paid
        return OperationStatus.Success
    }

    override fun getStatus() : OrderStatus {
       return order.status
    }

    override fun setStatus(status : OrderStatus) {
        order.status = status
    }

    override fun getTime(): Long {
        synchronized(locker1) {
            var time = 0
            order.dishes.forEach { time += it.time }
            return (time * 300).toLong()
        }
    }

    override fun getID(): Int {
        return order.id
    }
}