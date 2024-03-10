package com.example.register

import com.example.logger.Logger
import kotlinx.serialization.Serializable

@Serializable
open class User(var name : String, var login : String, var password : String) {
    private var logStatus = false
    var role = Role.Guest
    fun isLogged() : Boolean {
        return logStatus
    }
    fun logIn() {
        logStatus = true
    }
    fun logOut() {
        logStatus = false
    }
}

enum class Role {
    Admin,
    Guest
}