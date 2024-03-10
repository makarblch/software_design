package com.example.plugins

import com.example.register.Admin
import com.example.register.Guest
import com.example.register.Role
import com.example.storage.Storage

fun checkerAdmin(data : Array<String>) : Boolean {
    val person = Storage.userList.find { it.login == data[0] }
    return person != null && person.role == Role.Admin && person.isLogged()
}

fun checkerAdmin(data : String) : Boolean {
    val person = Storage.userList.find { it.login == data }
    return person != null && person.role == Role.Admin && person.isLogged()
}

fun checkerGuest(data : Array<String>) : Boolean {
    val person = Storage.userList.find { it.login == data[0] }
    return person != null && person.role == Role.Guest && person.isLogged()
}