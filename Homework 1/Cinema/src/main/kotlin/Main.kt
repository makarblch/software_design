const val SIZE = 10 // size of the cinema hall
var employees: MutableSet<Employee> = mutableSetOf()


fun main(args: Array<String>) {
    // Creating an object of management system
    val system = System()
    // Creating .json files
    Serializer.createFiles()
    // Starting using a program.
    while (true) {
        // Authentication
        val employee = Authentification.authentication()
        system.employee = employee
        // Starting a user session for a current employee
        var userSession = true
        while (userSession) {
            Serializer.deserialize(system)
            // Show employee a command menu
            system.menu()
            println("Do you want to exit? Write down Yes/No")
            val reply = readln().lowercase()
            if (reply == "yes" || reply == "да" || reply == "si") {
                userSession = false
                employee.logOut()
            }
            Serializer.serialize(system)
        }
        println("Do you want to finish the program? Write down Yes/No")
        val reply = readln().lowercase()
        if (reply == "yes" || reply == "да" || reply == "si") {
            break
        }
    }
}