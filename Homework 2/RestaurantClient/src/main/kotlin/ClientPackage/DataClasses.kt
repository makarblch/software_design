package ClientPackage

import Storage.Role
import kotlinx.serialization.Serializable

@Serializable
internal data class User(var name : String, var login : String, var password : String) {
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

@Serializable
internal data class Review (var rate : String, var comment : String)

