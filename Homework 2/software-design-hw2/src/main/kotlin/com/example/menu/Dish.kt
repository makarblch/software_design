package com.example.menu

import kotlinx.serialization.Serializable

@Serializable
data class Dish (var ordNumber : Int, var name : String, var price : Int, var time : Int) {
}