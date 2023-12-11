class User(val login : String, val password : String) {
    var goods : MutableList<Good> = mutableListOf()

    var isLogged : Boolean = true // No actions or transactions are available for unlogged users

    var cardNumber : Long = 0 // number of credit/debit card

    var balance : Long = 1000 // primarly balance = 1000

    fun logIn() {
        isLogged = true
    }

    fun logOut() {
        isLogged = false
    }

    override fun toString(): String {
        return "User $login"
    }
}