import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.security.MessageDigest
import java.lang.StringBuilder
/**
 * A companion class for signing up a new employee and creating a password
 */
object Authentification {
    fun authentication() : Employee {
        println("Hello! Welcome to our cinema managing system!\n" +
                "To continue using it you will need to log in. Do you have an account?")
        val reply = readln().lowercase()
        if (reply == "yes" || reply == "да" || reply == "si") {
            print("Input your login: ")
            val login = readln()
            val employee = employees.find { it.login == login }
            if (employee == null) {
                println("No such user found! Try to sign up")
                return signUp()
            }
            employee.logIn()
            return employee
        }
        return signUp()
    }
    /**
     * Function for signing up new employee
     */
    fun signUp() : Employee {
        print("Input your login: ")
        val login = readln()
        print("Input your password: ")
        var password = readln()
        var repeatPassword : String
        // Repeating password attempts unless they are equal
        while (true) {
            print("Repeat your password: ")
            repeatPassword = readln()
            if (password == repeatPassword) {
                break
            }
            println("Ooops! These passwords do not match. Try again!")
            print("Input your password: ")
            password = readln()
        }
        println("Successfully signed up!")
        // Adding new employee to the common list. Add encrypting!
        val employee = Employee(login, encryptPassword(password))
        // Signed up person is straight away logged in
        employee.isLogged = true
        employees.add(employee)
        return employee
    }

    private fun getHash(inByte : ByteArray) : String {
        var bytes = MessageDigest.getInstance("SHA-256").digest(inByte)
        return with (StringBuilder()) {
            bytes.forEach { byte -> append(String.format("%02X", byte)) }
            toString().lowercase()
        }
    }
    /**
     * A function to encrypt the password
     */
    fun encryptPassword(password : String) : String{
        return getHash(password.toByteArray())
    }
}