package com.example.kitchen

import com.example.order.ConcreteOrderBuilder
import com.example.order.Order


const val MAXIMAL = 3


object Kitchen {
    val queue : MutableList<Order> = mutableListOf()

    var numOrders = 0

    var orderBuilder = ConcreteOrderBuilder()
}