import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileWriter

fun createGoods() {
    // creating list of goods
    goods.add(Good("charger", 1000, 2))
    goods.add(Good("book", 300, 1))
    goods.add(Good("love", 10, 100))
    goods.add(Good("perfume", 5000, 4))
    goods.add(Good("eraser", 100, 300))
    goods.add(Good("lubricant", 500, 5))
}

fun serialization() {
    // serialising list of goods (for example, if we want to open a new shop with same assortment
    val writer : FileWriter = FileWriter("goods.txt", true)
    for (element in goods) {
        val json = Json.encodeToString(element)
        writer.write(json)
    }
    writer.flush()
}
fun viewCatalog(user : User?) {
    // only for authorized users
    if (user != null) {
        if (!user.isLogged) {
            println("You are not authorized!")
            return
        }
    }
    for (good in goods) {
        print(good)
    }
    print("\n")
}

fun buyGood(user: User?, good : Good) : Boolean {
    if (user != null) {
        if (!user.isLogged) {
            println("You are not authorized!")
            return false
        }
    }
    if (good.count > 0) {
        if (payGood(user, good)) {
            return true
        }
        return false
    } else {
        println("No such goods available :(")
        return false
    }
}

private fun payGood(user: User?, good : Good) : Boolean{
    if (user != null) {
        if (user.cardNumber.toInt() == 0) {
            println("You should add your debit/credit card to your account!")
            println("Enter your card number")
            user.cardNumber = readln().toLong()
        }
    }
    if (user != null) {
        if (user.balance < good.price) {
            println("Not enough money!")
            return false
        }
    }
    if (user != null) {
        user.balance -= good.price
        user.goods.add(good)
        --good.count
    }
    return true
}

fun enter() : User? {
    val result = readln().lowercase()
    if (result == "yes") {
        println("Enter your login and password")
        val login : String = readln()
        val password : String = readln()
        val boobs = users.find {it.login == login && it.password == password}
        return if (boobs != null) {
            boobs.logIn()
            val user = users.find {it.login == login}
            println("Successfully log in!")
            user
        } else {
            println("No such user. Try to sign up")
            val user = signUp()
            println("Successfully signed up!")
            println(user)
            user
        }
    } else {
        println("Let's Sign Up")
        val user = signUp()
        println("Successfully signed up!")
        println(user)
        return user
    }
}

fun signUp() : User? {
    println("Write down your login")
    val login : String = readln()
    println("Write down your password")
    val password : String = readln()
    while (!passwordCheck(password)) {
        println("Ooops! Passwords do not match, try again")
    }
    users.add(User(login, password))
    return users.find {it.login == login}
}

fun passwordCheck(pass : String) : Boolean{
    println("Repeat your password")
    val rep = readln()
    return pass == rep
}

fun action(user: User?) {
    println("Choose what would you like to purchase: ")
    val title : String = readln().lowercase()
    val good = goods.find { it.title == title}
    if (good != null) {
        val res = buyGood(user, good)
        if (res) {
            println("Successfully bought!")
        }
    } else {
        println("This good has not been found :(")
    }
}