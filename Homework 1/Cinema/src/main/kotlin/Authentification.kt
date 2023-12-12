/**
 * A class for signing up a new employee and creating a password
 */
class Authentification {
    fun authentification() : Boolean {
        println("Hello! Welcome to our cinema managing system!\n" +
                "To continue using it you will need to log in. Do you have an account?")
        val reply = readln().lowercase()
        if (reply == "yes" || reply == "да" || reply == "si") {
            print("Input your login: ")
            val login = readln()
            val employee = employees.find { it.Login == login }
            if (employee == null) {
                println("No such user found! Try to sign up")
                return false
            }
            employee.logIn()
            return true
        }
        return false
    }
    /**
     * Function for signing up new employee
     */
    fun signUp() {
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
        employee.logIn()
        employees.add(employee)
    }

    /**
     * A function to encrypt the password
     */
    private fun encryptPassword(password : String) : String{
        return password.lowercase()
    }
}