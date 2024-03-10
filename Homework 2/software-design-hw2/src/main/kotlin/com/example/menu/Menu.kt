package com.example.menu

import com.example.order.OperationStatus
import kotlinx.serialization.Serializable

@Serializable
class Menu (var dishes : MutableMap<Dish, Int>) {

    fun addDish(ordNumber : Int, name : String, price : Int, time : Int, count : Int) : OperationStatus {
        if (dishes.keys.find {it.name == name} != null) {
            return OperationStatus.Fail
        }
        if (price >= 0 && time > 0 && count > 0) {
            val dish = Dish(ordNumber, name, price, time)
            println(dishes)
            dishes[dish] = count
            return OperationStatus.Success
        }
        return OperationStatus.Fail
    }

    fun deleteDish(name : String) {
        val res = dishes.keys.find {it.name == name}
        if (res != null) {
            val index = dishes.keys.indexOf(res)
            dishes.remove(res)
            for (element in dishes.keys.drop(index)) {
                --element.ordNumber
            }
        }
    }

    fun changeDish(name : String, pars : Array<String>) : Boolean {
        val dish = dishes.keys.find {it.name == name}
        if (dish != null) {
            try {
                when (pars.size) {
                    3 -> {
                        dish.name = pars[2]
                        return true
                    }
                    4 -> {
                        val priceTemp = pars[3].toIntOrNull()
                        return if (priceTemp != null) {
                            dish.name = pars[2]
                            dish.price = priceTemp
                            true
                        } else {
                            false
                        }
                    }
                    5 -> {
                        val priceTemp = pars[3].toIntOrNull()
                        val timeTemp = pars[4].toIntOrNull()
                        return if (priceTemp != null && timeTemp != null) {
                            dish.name = pars[2]
                            dish.price = priceTemp
                            dish.time = timeTemp
                            true
                        } else {
                            false
                        }
                    }
                }
                return true
            } catch (e : TypeCastException) {
                return false
            } catch (e : Exception) {
                return false
            }
        }
        return false
    }

    fun findDish(index : String) : Dish? {
        return dishes.keys.find { it.ordNumber == index.toInt() }
    }

    fun findDishName(name : String) : Dish? {
        return dishes.keys.find { it.name == name }
    }

    fun getNumberOfDishes() : Int {
        var count = 0
        for (element in dishes) {
            ++count
        }
        return count
    }

    fun prettifyMenu() : String {
        val response = buildString {
            append("Menu:\n\n")
            dishes.keys.forEach { dish ->
                append("${dish.ordNumber}. ${dish.name}, price: ${dish.price}. Time required: ${dish.time}\n")
            }
            append("\nBon appetit!")
        }
        return response
    }

    fun checkDishesAbsence(dis : MutableList<Dish>) : Boolean {
        for (dish in dis) {
            val res = findDishName(dish.name)
            if (dishes[res] == 0) {
                return true
            }
        }
        return false
    }
}