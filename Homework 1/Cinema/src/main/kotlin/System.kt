import kotlin.random.Random
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat

interface IChangeSession {
    fun changeSessionMovie(id: Int)
    fun changeSessionTime(id: Int)
}

interface IChangeMovie {
    fun changeMovieName(movie: String)
    fun changeMovieDirector(movie: String)
    fun changeMovieDuration(movie: String)
}

class System : IChangeMovie, IChangeSession {
    private var movies: MutableList<Movie> = mutableListOf()
    private var sessions: MutableList<Session> = mutableListOf()
    private var tickets: MutableList<Ticket> = mutableListOf()
    private var index: Int = 0
    lateinit var employee: Employee


    private fun check() {
        if (!employee.isLogged) {
            println("You are not logged in!")
            employee.logIn()
        }
    }

    fun menu() {
        check()
        println(
            "Choose a command (write a number): \n1. Add new movie\n2. Change existing movie name\n" +
                    "3. Change existing movie director\n4. Change existing movie duration\n" +
                    "5. Add new session\n6. Change existing session movie" +
                    "7. Change existing session time\n8.Show cinema hall for a specific session\n" +
                    "9. Sell a ticket\n10. Return a ticket"
        )
        val option = readln().toInt()
        activity(option)
    }

    private fun activity(command: Int) {
        // Add movie
        if (command == 1) {
            addMovie()
        } else if (command in 2..4) {
            print("Input a current movie name: ")
            val movie = readln()
            when (command) {
                2 -> changeMovieName(movie)
                3 -> changeMovieDirector(movie)
                4 -> changeMovieDuration(movie)
            }
            // Add session
        } else if (command == 5) {
            addSession()
        } else if (command in 6..9) {
            print("Input a current session Id: ")
            val id = readln().toInt()
            when (command) {
                6 -> changeSessionMovie(id)
                7 -> changeSessionTime(id)
                8 -> show(id)
                9 -> sellTicket(id)
            }
        } else if (command == 10) {
            print("Input the ticketId: ")
            val tickedId = readln().toInt()
            returnTicket(tickedId)
        } else {
            println("No such command :(")
        }
    }

    fun addMovie() {
        check()
        print("Input name of a new movie: ")
        val name = readln()
        print("Input name of director's last name: ")
        val director = readln()
        print("Input a duration of the movie (in minutes): ")
        val duration = readln().toInt()
        val movie = Movie(name, director, duration)
        movies.add(movie)
    }

    fun addSession() {
        check()
        print("Input name of the movie: ")
        val name = readln()
        val movie = movies.find { it.name == name }
        if (movie == null) {
            println("No such movie :( Try to add new movie before creating a session")
        } else {
            print("Input date and time of the session: ")
            var date = readln()
            while (!dateValidation(date)) {
               date = readln()
            }
            sessions.add(Session(index, movie, date))
            ++index
        }
    }

    override fun changeMovieName(movie: String) {
        check()
        val current = movies.find { it.name == movie }
        if (current == null) {
            println("No such movie :(")
        } else {
            print("Input a new movie name: ")
            val name = readln()
            current.name = name
        }
    }

    override fun changeMovieDirector(movie: String) {
        check()
        val current = movies.find { it.name == movie }
        if (current == null) {
            println("No such movie :(")
        } else {
            print("Input a new movie director: ")
            val director = readln()
            current.director = director
        }
    }

    override fun changeMovieDuration(movie: String) {
        check()
        val current = movies.find { it.name == movie }
        if (current == null) {
            println("No such movie :(")
        } else {
            print("Input a new movie duration: ")
            val duration = readln().toInt()
            current.duration = duration
        }
    }

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
            }
        }
    }

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
        }
    }

    fun show(sessionId: Int) {
        check()
        val session = sessions.find { it.id == sessionId }
        if (session == null) {
            println("No such session :(")
        } else {
            session.showPlaces()
        }
    }

    /**
     * Functions that sells tickets
     * @param name name of movie
     */
    fun sellTicket(sessionId: Int) {
        check()
        val session = sessions.find { it.id == sessionId }
        if (session == null) {
            println("No such session :(")
        } else {
            // variables for row and column number
            var row: Int
            var column: Int
            // Generate random ticket number
            val generator = Random.nextInt(1000000)
            // While place is not available, repeat
            while (true) {
                print("Input a row number")
                row = readln().toInt()
                print("Input a column number")
                column = readln().toInt()
                if (session.cinemaHall[row][column] == 0) {
                    println("Successfully ordered!")
                    val ticket = Ticket(generator, session, row, column)
                    tickets.add(ticket)
                    break
                }
                println("Ooops! This place is already booked :( Try again!")
            }
        }
    }

    fun returnTicket(ticketId: Int): Boolean {
        check()
        val ticket = tickets.find { it.ticketNr == ticketId }
        // checking if there is no such ticket
        if (ticket == null) {
            println("No such ticket :(")
            return false
        }
        val session = ticket.session
        // Making a place free
        session.cinemaHall[ticket.row][ticket.column] = 0
        // Removing the ticket from list
        tickets.removeIf { it.ticketNr == ticketId }
        return true
    }

    fun deserialize() {
        try {
            var read = File("movies.json").readText(Charsets.UTF_8)
            if (read != "") {
                movies = Json.decodeFromString<MutableList<Movie>>(read)
            }
            read = File("sessions.json").readText(Charsets.UTF_8)
            if (read != "") {
                sessions = Json.decodeFromString<MutableList<Session>>(read)
            }
            read = File("tickets.json").readText(Charsets.UTF_8)
            if (read != "") {
                tickets = Json.decodeFromString<MutableList<Ticket>>(read)
            }
        } catch (ex : IOException) {
            println("Unable to read files :(")
        } catch (_ : Exception) {
            println("Ooops! Something went wrong!")
        }
    }

    fun serialize() {
        try {
            if (movies.size != 0) {
                File("movies.json").writeText(Json.encodeToString<MutableList<Movie>>(movies))
            }
            if (sessions.size != 0) {
                File("sessions.json").writeText(Json.encodeToString<MutableList<Session>>(sessions))
            }
            if (tickets.size != 0) {
                File("tickets.json").writeText(Json.encodeToString<MutableList<Ticket>>(tickets))
            }
        } catch (ex : IOException) {
            println("Unable to write files :(")
        } catch (_ : Exception) {
            println("Ooops! Something went wrong!")
        }
    }
    fun createFiles() {
        try {
            File("movies.json").createNewFile()
            File("sessions.json").createNewFile()
            File("tickets.json").createNewFile()
        } catch (e: IOException) {
            println("Unable to create files :(")
        } catch (_: Exception) {
            println("Ooops! Something went wrong")
        }
    }

    fun dateValidation (date: String?): Boolean {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        formatter.isLenient = false
        return try {
            formatter.parse(date)
            true
        } catch (e: Exception) {
            println("Incorrect date :(")
            false
        }
    }

}