import ManagerSystem.ManagerSystem
import ManagerSystem.redColor
import ManagerSystem.reset
import Storage.Role

val incorrectInput = redColor + "Incorrect input! Try again!" + reset

fun main(args: Array<String>) {
    val system = ManagerSystem()
    system.start()
    while (true) {
        val res = system.role()
        println(res)
        if (res !in arrayOf(incorrectInput, "Such user already exists, try again!", "Failed to log in!")) {
            break
        }
        system.start()
    }
    var result = ""
    if (system.client.userType == Role.Admin) {
        while (true) {
            system.overviewAdmin()
            println("Please, choose command or log out if you want to finish the session")
            result = system.commands()
            println(result)
            if (result == "Logged out successfully!") {
                break
            }
        }
    } else {
        while (true) {
            system.overviewGuest()
            println("Please,  choose command or log out if you want to finish the session")
            result = system.commands()
            println(result)
            if (result == "Logged out successfully!") {
                break
            }
        }
    }
    println("Thank you! Have a nice day!")
    system.client.disconnect()
}