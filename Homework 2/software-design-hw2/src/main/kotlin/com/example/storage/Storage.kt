package com.example.storage

import com.example.menu.Dish
import com.example.menu.Menu
import com.example.order.Order
import com.example.register.User
import com.example.review.Review
import kotlinx.serialization.Serializable

@Serializable
object Storage {
    var userList : MutableList<User> = mutableListOf()
    var dish1 = Dish(1, "Fish Chestnuts", 200, 20)
    var dish2 = Dish(2, "Fish Hedgehogs", 300, 30)
    var menu = Menu(mutableMapOf(Pair(dish1, 10), Pair(dish2, 20)))
    var orderList : MutableList<Order> = mutableListOf()
    var reviewList : MutableList<Review> = mutableListOf()
    var income = 0
    var ID = 0
}