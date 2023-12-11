import kotlin.collections.*

var goods: MutableList<Good> = mutableListOf()
var users: MutableList<User> = mutableListOf()

fun main(args: Array<String>) {
    createGoods()
    serialization()
    println("Welcome to our shop! Already have an account? Write YES or NO");
    val user = enter()
    println("Wanna see list of goods? Write YES or NO")
    val ans2 : String = readln().lowercase()
    if (ans2 != "no") {
        viewCatalog(user)
    }
    println("Wanna buy something? Write YES or NO")
    var ans1 : String = readln().lowercase()
    while (ans1 != "no") {
        action(user)
        println("Wanna buy something again? Write YES or NO")
        ans1 = readln().lowercase()
    }
    println("Your balance: ${user?.balance}")
    println("Thank you! Goodbye :)")
    user?.logOut()
}