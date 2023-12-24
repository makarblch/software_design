import kotlin.random.Random
import java.text.SimpleDateFormat

/**
 * Interface for methods of changing sessions
 */
interface ChangeSession {
    fun changeSessionMovie(id: Int)
    fun changeSessionTime(id: Int)
}

/**
 * Interface for methods of changing movies
 */
interface ChangeMovie {
    fun changeMovieName(movie: String)
    fun changeMovieDirector(movie: String)
    fun changeMovieDuration(movie: String)
}

/**
 * A managing system class. All methods are implemented here.
 */
class System : ChangeMovie, ChangeSession {
    var movies: MutableList<Movie> = mutableListOf()
    var sessions: MutableList<Session> = mutableListOf()
    var tickets: MutableList<Ticket> = mutableListOf()
    var index: Int = 0
    lateinit var employee: Employee


    /**
     * Checking whether the employee is logged in or not
     */
    private fun check() {
        if (!employee.isLogged) {
            println("You are not logged in!")
            employee.logIn()
        }
    }

    /**
     * Shows main commands
     */
    fun menu() {
        check()
        println(
            "Choose a command (write a number): \n1. Add new movie\n2. Change existing movie name\n" +
                    "3. Change existing movie director\n4. Change existing movie duration\n" +
                    "5. Add new session\n6. Change existing session movie\n" +
                    "7. Change existing session time\n8. Show cinema hall for a specific session\n" +
                    "9. Sell a ticket\n10. Return a ticket\n11. Mark a visitor\n" +
                    "12. Show all movies\n13. Show all sessions"
        )
        val option = readln().toIntOrNull() ?: 14
        activity(option)
    }

    /**
     * Matches the command id with the command implementation
     */
    private fun activity(command: Int) {
        // Add movie
        when (command) {
            1 -> {
                addMovie()
            }
            in 2..4 -> {
                print("Input a current movie name: ")
                val movie = readln()
                when (command) {
                    2 -> changeMovieName(movie)
                    3 -> changeMovieDirector(movie)
                    4 -> changeMovieDuration(movie)
                }
                // Add session
            }
            5 -> {
                addSession()
            }
            in 6..9 -> {
                print("Input a current session Id: ")
                val id = readln().toIntOrNull() ?: -1
                when (command) {
                    6 -> changeSessionMovie(id)
                    7 -> changeSessionTime(id)
                    8 -> show(id)
                    9 -> sellTicket(id)
                }
            }
            10 -> {
                print("Input the ticketId: ")
                val tickedId = readln().toIntOrNull() ?: -1
                returnTicket(tickedId)
            }
            11 -> {
                print("Input the ticketId: ")
                val tickedId = readln().toIntOrNull() ?: -1
                markVisitor(tickedId)
            }
            12 -> {
                showMovies()
            }
            13 -> {
                showSessions()
            }
            else -> {
                println("No such command :(")
            }
        }
    }

    /**
     * Adding movie to the movie list
     */
    private fun addMovie() {
        check()
        print("Input name of a new movie: ")
        val name = readln()
        print("Input name of director's last name: ")
        val director = readln()
        print("Input a duration of the movie (in minutes): ")
        val duration = readln().toIntOrNull() ?: 0
        val movie = Movie(name, director, duration)
        movies.add(movie)
        println("Successfully added!")
    }

    /**
     * Adding session to the session list
     */
    private fun addSession() {
        check()
        print("Input name of the movie: ")
        val name = readln()
        val movie = movies.find { it.name == name }
        if (movie == null) {
            println("No such movie :( Try to add new movie before creating a session")
        } else {
            print("Input date and time of the session (dd/MM/yyyy HH:mm) : ")
            var date = readln()
            while (!dateValidation(date)) {
               date = readln()
            }
            sessions.add(Session(index, movie, date))
            println("Successfully added! Session id: $index")
            ++index
        }
    }

    /**
     * Changing movie name
     * @param movie name of movie
     */
    override fun changeMovieName(movie: String) {
        check()
        val current = movies.find { it.name == movie }
        if (current == null) {
            println("No such movie :(")
        } else {
            print("Input a new movie name: ")
            val name = readln()
            current.name = name
            println("Successfully changed!")
        }
    }

    /**
     * Changing movie director
     * @param movie name of movie
     */
    override fun changeMovieDirector(movie: String) {
        check()
        val current = movies.find { it.name == movie }
        if (current == null) {
            println("No such movie :(")
        } else {
            print("Input a new movie director: ")
            val director = readln()
            current.director = director
            println("Successfully changed!")
        }
    }

    /**
     * Changing movie duration
     * @param movie name of movie
     */
    override fun changeMovieDuration(movie: String) {
        check()
        val current = movies.find { it.name == movie }
        if (current == null) {
            println("No such movie :(")
        } else {
            print("Input a new movie duration: ")
            val duration = readln().toIntOrNull() ?: 0
            if (duration == 0) {
                println("Due to some problems, duration is currently 0")
            } else {
                println("Successfully changed!")
            }
            current.duration = duration
        }
    }

    /**
     * Changing movie of the session
     * @param id session id
     */
    override fun changeSessionMovie(id: Int) {
        check()
        val session = sessions.find { it.id == id }
        if (session == null) {
            println("No such session :(")
        } else {
            print("Input a new session movie name: ")
            val name = readln()
            val movie = movies.find { it.name == name }
            if (movie == null) {  // movie was not found
                println("No such movie found :( Try to add a new movie before changing a session")
            } else {
                session.movie = movie
                println("Successfully changed!")
            }
        }
    }

    /**
     * Changing time of the session
     * @param id session id
     */
    override fun changeSessionTime(id: Int) {
        check()
        val session = sessions.find { it.id == id }
        if (session == null) {
            println("No such session :(")
        } else {
            print("Input a new session time: ")
            var date = readln()
            while (!dateValidation(date)) {
                date = readln()
            }
            session.time = date
            println("Successfully changed!")
        }
    }

    /**
     * Shows all cinema hall places of the session
     * @param sessionId session id
     */
    private fun show(sessionId: Int) {
        check()
        val session = sessions.find { it.id == sessionId }
        if (session == null) {
            println("No such session :(")
        } else {
            session.showPlaces()
        }
    }

    /**
     * Shows all available movies
     */
    private fun showMovies() {
        for (element in movies) {
            println(element)
        }
    }

    /**
     * Shows all available sessions
     */
    private fun showSessions() {
        for (element in sessions) {
            println(element)
        }
    }

    /**
     * Functions that sells tickets
     * @param name name of movie
     */
    private fun sellTicket(sessionId: Int) {
        check()
        val session = sessions.find { it.id == sessionId }
        if (session == null) {
            println("No such session :(")
        } else {
            // Show the cinema hall scheme
            println("Выберите место : ")
            show(session.id)
            // Variables for row and column number
            var row: Int
            var column: Int
            // Generate random ticket number
            val generator = Random.nextInt(1000000)
            // While place is not available, repeat
            while (true) {
                print("Input a row number: ")
                row = readln().toIntOrNull() ?: -1
                print("Input a column number: ")
                column = readln().toIntOrNull() ?: -1
                if (row == -1 || column == -1 || row >= 10 || column >= 10) {
                    println("Incorrect row/column number! Try again!")
                    continue
                }
                if (session.cinemaHall[row][column] == 0) {
                    println("Successfully ordered!")
                    val ticket = Ticket(generator, session, row, column)
                    tickets.add(ticket)
                    println("Your ticket number: ${ticket.ticketNr}")
                    // Change place status since it has been ordered
                    session.cinemaHall[row][column] = 1
                    break
                }
                println("Ooops! This place is already booked :( Try again!")
            }
        }
    }

    /**
     * Returns a ticket
     * @param ticketId ticket id
     */
    private fun returnTicket(ticketId: Int): Boolean {
        check()
        val ticket = tickets.find { it.ticketNr == ticketId }
        // checking if there is no such ticket
        if (ticket == null) {
            println("No such ticket :(")
            return false
        }
        val session = sessions.find { it.id == ticket.session.id} ?: return false
        // Making a place free
        session.cinemaHall[ticket.row][ticket.column] = 0
        // Removing the ticket from list
        tickets.removeIf { it.ticketNr == ticketId }
        println("Successfully returned!")
        return true
    }

    /**
     * Markes a visitor of a session
     * @param ticketId ticket id
     */
    private fun markVisitor(ticketId: Int) : Boolean {
        check()
        val ticket = tickets.find { it.ticketNr == ticketId }
        // checking if there is no such ticket
        if (ticket == null) {
            println("No such ticket :(")
            return false
        }
        val session = ticket.session
        // Put a tick on visitor's place (which means that he came)
        session.sessionVisitors[ticket.row][ticket.column] = 1
        println("Successfully! Enjoy ${ticket.session.movie}!")
        return true
    }

    /**
     * Checkes if given date is correct
     */
    private fun dateValidation (date: String?): Boolean {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        formatter.isLenient = false
        return try {
            formatter.parse(date)
            true
        } catch (e: Exception) {
            println("Incorrect date :( Try again! Input date: ")
            false
        }
    }
}