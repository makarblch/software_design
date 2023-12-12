const val SIZE = 10 // size of the cinema hall
var employees: MutableList<Employee> = mutableListOf()


fun main(args: Array<String>) {
    // Creating an object of our management system
    var system: System
    // Creating an object of authentification
    val auth = Authentification()
    while (true) {
        if (!auth.authentification()) {
            auth.signUp()
        }
        // Some actions
        println("Do you want to exit?")
        val reply = readln().lowercase()
        if (reply == "yes" || reply == "да" || reply == "si") {
            break
        }
    }
}