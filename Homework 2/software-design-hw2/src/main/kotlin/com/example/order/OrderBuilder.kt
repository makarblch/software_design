package com.example.order

interface OrderBuilder {
    fun addDish(dish: String) : OperationStatus
    fun deleteDish(dish : String) : OperationStatus
    fun cancel() : OperationStatus
    fun payCheque() : OperationStatus
    fun getStatus() : OrderStatus
    fun setStatus(status: OrderStatus)
    fun getTime() : Long
    fun getID() : Int
}