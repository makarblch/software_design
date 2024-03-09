package com.example.manager

import com.example.logger.Logger
import com.example.order.OperationStatus
import com.example.register.*
import com.example.serializer.Serializer
import com.example.storage.Storage

object UserManager {
    fun addAdmin(data: User): OperationStatus {
        val person = Storage.userList.find { it.login == data.login }
        return if (person == null) {
            val admin = Admin(data.name, data.login, encryptPassword(data.password))
            Logger.log("User ${data.login} signed up as admin successfully")
            admin.logIn()
            admin.role = Role.Admin
            Storage.userList.add(admin)
            Serializer.refresh()
            OperationStatus.Success
        } else {
            Logger.log("User ${data.login} failed to sign up as admin")
            OperationStatus.Fail
        }
    }

    fun addGuest(data: User): OperationStatus {
        val person = Storage.userList.find { it.login == data.login }
        return if (person == null) {
            val guest = Guest(data.name, data.login, encryptPassword(data.password))
            guest.logIn()
            Storage.userList.add(guest)
            Logger.log("User ${data.login} signed up as guest successfully")
            Serializer.refresh()
            OperationStatus.Success
        } else {
            Logger.log("User ${data.login} failed to sign up as guest")
            OperationStatus.Fail
        }
    }

    fun logIn(data: User): OperationStatus {
        val person = Storage.userList.find { it.login == data.login && it.name == data.name }
        return if (person != null) {
            if (encryptPassword(data.password) == person.password) {
                person.logIn()
                Logger.log("User ${data.login} logged in as ${data.role}")
                Serializer.refresh()
                OperationStatus.Success
            } else {
                Logger.log("User ${data.login} tried to log in with incorrect password")
                OperationStatus.Fail
            }
        } else {
            Logger.log("User ${data.login} tried to log but wasn't found")
            OperationStatus.Forbidden
        }
    }

    fun logOut(data: String): OperationStatus {
        val person = Storage.userList.find { it.login == data }
        return if (person != null) {
            person.logOut()
            Logger.log("User $data logged out")
            Serializer.refresh()
            OperationStatus.Success
        } else {
            Logger.log("User $data failed to log out")
            OperationStatus.Fail
        }
    }
}
