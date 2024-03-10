package ManagerSystem

import ClientPackage.Client
import Storage.Role
import incorrectInput

val greenColor = "\u001b[32m"
val yellowColor = "\u001b[33m"
val purpleColor = "\u001b[35m"
val redColor = "\u001b[31m"
val reset = "\u001b[0m"

class ManagerSystem {
    var client = Client()

    fun start() {
        println(greenColor + "Welcome to out restaurant! We're pleased that you decided to visit us today." + reset)
        println("To start using the application, please log in or sign up. Possible actions: ")
        println("1. Sign up as an admin")
        println("2. Sign up as a guest")
        println("3. Log in as admin")
        println("4. Log in as guest")
    }

    fun overviewGuest() {
        println(yellowColor + "Here is the list of available actions: " + reset)
        println("1. See menu")
        println("2. Make order")
        println("3. Add dish to the order")
        println("4. Delete dish from the order")
        println("5. Cancel the order")
        println("6. Pay the order")
        println("7. Check status of the order")
        println("8. Log out")
        println("9. Add review")
    }

    fun overviewAdmin() {
        overviewGuest()
        println(purpleColor + "Only for admins:" + reset)
        println("10. Add dish to the menu")
        println("11. Change dish from the menu")
        println("12. Delete dish from the menu")
        println("13. Get most popular dish from the menu")
        println("14. Get average rate of orders")
        println("15. Get percentage of positive rates of orders")
    }

    fun role() : String {
        var command = readln().toIntOrNull()
        if (command == null || command < 1 || command > 4) {
            println(incorrectInput)
            return incorrectInput
        }
        println("Please, input your name: ")
        val name = readln()
        println("Please, input your login: ")
        val login = readln()
        println("Please, input your password: ")
        val password = readln()
        when (command) {
            1 -> {
                return client.signUpAdmin(name, login, password)
            }
            2 -> {
                return client.signUpGuest(name, login, password)
            }
            3 -> {
                client.userType = Role.Admin
                return client.login(name, login, password)
            }
            4 -> {
                client.userType = Role.Guest
                return client.login(name, login, password)
            }
            else -> {
                println(incorrectInput)
                return incorrectInput
            }
        }

    }

    fun commands() : String {
        var command = readln().toIntOrNull()
        if (command == null || command < 1 || command > 15) {
            return redColor + "Incorrect input! Try again!" + reset
        }
        if (client.userType == Role.Guest && command > 9) {
            return redColor + "Incorrect input! Try again!" + reset
        }
        when (command) {
            1 -> {
                return client.getMenu()
            }
            2 -> {
                return makeOrderInput()
            }
            3 -> {
                return addDishInput()
            }
            4 -> {
                return deleteDishInput()
            }
            5 -> {
                println("Please, input number of your order")
                val order = readln()
                return client.cancelOrder(order)
            }
            6 -> {
                println("Please, input number of your order")
                val order = readln()
                return client.payOrder(order)
            }
            7 -> {
                println("Please, input number of your order")
                val order = readln()
                return client.getStatus(order)
            }
            8 -> {
                return client.logout()
            }
            9 -> {
                return addReviewOrder()
            }
            10 -> {
                return addDishMenu()
            }
            11 -> {
                return changeDishMenu()
            }
            12 -> {
                return deleteDishMenu()
            }
            13 -> {
                return client.getPopularDish()
            }
            14 -> {
                return client.getAverageReview()
            }
            15 -> {
                return client.getPositiveReview()
            }
            else -> {
                return incorrectInput
            }
        }
    }

    fun makeOrderInput() : String {
        println("Please, choose the position of the dish you'd like to order and input it." +
                "Input \"-\" when you're ready")
        var dish = "-1"
        var list : MutableList<String> = mutableListOf()
        while (dish != "-") {
            dish = readln()
            list.add(dish)
        }
        list.removeLast()
        return client.makeOrder(list.toTypedArray())
    }

    fun addDishInput() : String {
        println("Please, input number of your order: ")
        val order = readln()
        println("...and the position of the dish you'd like to add")
        val dish = readln()
        return client.addDish(arrayOf(order, dish))
    }

    fun deleteDishInput() : String {
        println("Please, input number of your order: ")
        val order = readln()
        println("...and the position of the dish you'd like to delete")
        val dish = readln()
        return client.deleteDish(arrayOf(order, dish))
    }

    fun addDishMenu() : String {
        println("Please, input the name of a new dish")
        val name = readln()
        println("Please, input the price of a new dish (in rubles)")
        val price = readln()
        println("Please, input the time of a new dish (in minutes)")
        val time = readln()
        println("Please, input the quantity a new dish (in units)")
        val count = readln()
        return client.addDish(name, price, time, count)
    }

    fun changeDishMenu() : String {
        println("You can change:\n" +
                "1. only the name of the dish,\n" +
                "2. name + price,\n," +
                "3. name + price + time\n")
        val choice = readln().toIntOrNull()
        if (choice == null || choice < 1 || choice > 3) {
            return incorrectInput
        }
        print("Please, input the name of old dish")
        val nameOld = readln()
        print("Please, input the name of new dish")
        val nameNew = readln()
        when (choice) {
            1 -> {
                return client.changeDish(arrayOf(nameOld, nameNew))
            }
            2 -> {
                print("Please, input new price")
                val price = readln()
                return client.changeDish(arrayOf(nameOld, nameNew, price))
            }
            3 -> {
                print("Please, input new price")
                val price = readln()
                print("Please, input new time")
                val time = readln()
                return client.changeDish(arrayOf(nameOld, nameNew, price, time))
            }
            else -> {
                return incorrectInput
            }
        }
    }

    fun deleteDishMenu() : String {
        println("Please, input the name of the dish you'd like to delete")
        val name = readln()
        return client.deleteDish(name)
    }

    fun addReviewOrder() : String {
        println("Please, input the number of your order")
        val order = readln()
        println("Please, leave the rate of your order (1 to 5)")
        val rate = readln()
        println("Please, leave a comment about your impression")
        val comment = readln()
        return client.addReview(order, rate, comment)
    }

}