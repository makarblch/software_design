/**
 * A class that contains information about the cinema employee
 * @param name name of employee
 * @param lastName last name of employee
 */
class Employee(login : String, password : String) {
    private var login: String
    private var password : String

    var Login : String
        get() = login
        set(value) {
            login = value
        }
    init{
        this.login = login
        this.password = password
    }

    private var _isLogged : Boolean = false
    val IsLogged : Boolean
        get() = _isLogged

    /**
     * changes user's status isLogged to true
     */
    fun logIn() {
        _isLogged = true
    }

    /**
     * changes user's status isLogged to true
     */
    fun logOut() {
        _isLogged = false
    }

    override fun toString(): String {
        return "Employee $login"
    }
}