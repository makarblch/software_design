const val SIZE = 10 // size of the cinema hall
var employees: MutableList<Employee> = mutableListOf()


fun main(args: Array<String>) {
    // Creating an object of authentication system
    val auth = Authentification()
    // Creating an object of our management system
    val system = System()
    // Creating .json files
    system.createFiles()
    // Starting using a program.
    while (true) {
        // Authentication
        val employee = auth.authentication()
        system.employee = employee
        // Starting a user session for a current employee
        var userSession = true
        while (userSession) {
            system.deserialize()
            // Show employee a command menu
            system.menu()
            println("Do you want to exit?")
            val reply = readln().lowercase()
            if (reply == "yes" || reply == "да" || reply == "si") {
                userSession = false
                employee.logOut()
            }
            system.serialize()
        }
        println("Do you want to finish the program?")
        val reply = readln().lowercase()
        if (reply == "yes" || reply == "да" || reply == "si") {
            break
        }
    }
}