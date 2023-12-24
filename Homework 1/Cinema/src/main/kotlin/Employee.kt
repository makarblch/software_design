/**
 * A class that contains information about the cinema employee
 * @param name name of employee
 * @param lastName last name of employee
 */
class Employee(var login : String, private var password : String) {
    var isLogged : Boolean = false

    /**
     * changes user's status isLogged to true
     */
    fun logIn() {
        print("Write down your password: ")
        var attempt = readln()
        var encrypt = Authentification.encryptPassword(attempt)
        println("password : $password , encrypt: $encrypt")
        while (password != encrypt) {
            println("Incorrect password! Try again! ")
            print("Write down your password: ")
            attempt = readln()
            encrypt = Authentification.encryptPassword(attempt)
        }
        println("Successfully logged in!")
        isLogged = true
    }

    /**
     * changes user's status isLogged to true
     */
    fun logOut() {
        isLogged = false
    }

    override fun toString(): String {
        return "Employee $login"
    }
}