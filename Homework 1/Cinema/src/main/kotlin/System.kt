import java.time.LocalDateTime
import kotlin.random.Random

interface IChangeSession {
    fun changeSessionMovie(session: Session, movie : Movie)
    fun changeSessionTime(session: Session, time : LocalDateTime)
}
interface IChangeMovie {
    fun changeMovieName(movie: String, name : String)
    fun changeMovieDirector(movie: String, director : String)
    fun changeMovieDuration(movie: String, duration : Int)
}

class System : IChangeMovie, IChangeSession {
    var movies : MutableList<Movie> = mutableListOf()
    var sessions : MutableList<Session> = mutableListOf()
    var tickets : MutableList<Ticket> = mutableListOf()

    lateinit var employee : Employee

    fun addMovie(movie: Movie) {
        if (!employee.IsLogged) {
            println("You are not logged in!")
            employee.logIn()
        }
        movies.add(movie)
    }

    override fun changeMovieName(movie : String, name : String) {
        if (!employee.IsLogged) {
            println("You are not logged in!")
            employee.logIn()
        }
        val current = movies.find {it.Name == movie}
        if (current == null) {
            println("No such movie :(")
        } else {
            current.Name = name
        }
    }

    override fun changeMovieDirector(movie: String, director: String) {
        if (!employee.IsLogged) {
            println("You are not logged in!")
            employee.logIn()
        }
        val current = movies.find {it.Name == movie}
        if (current == null) {
            println("No such movie :(")
        } else {
            current.Director = director
        }
    }

    override fun changeMovieDuration(movie: String, duration: Int) {
        if (!employee.IsLogged) {
            println("You are not logged in!")
            employee.logIn()
        }
        val current = movies.find {it.Name == movie}
        if (current == null) {
            println("No such movie :(")
        } else {
            current.Duration = duration
        }
    }

    override fun changeSessionMovie(session: Session, movie: Movie) {
        if (!employee.IsLogged) {
            println("You are not logged in!")
            employee.logIn()
        }
        session.Movie = movie
    }

    override fun changeSessionTime(session: Session, time: LocalDateTime) {
        if (!employee.IsLogged) {
            println("You are not logged in!")
            employee.logIn()
        }
        session.Time = time
    }

    fun show(sessionId : Int) {
        val session = sessions.find { it.Id == sessionId }
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
    fun sellTicket(session: Session) {
        // variables for row and column number
        var row : Int
        var column : Int
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

    fun returnTicket(ticketId : Int) : Boolean {
        val ticket = tickets.find { it.TikcetNr == ticketId }
        // checking if there is no such ticket
        if (ticket == null) {
            println("No such ticket :(")
            return false
        }
        val session = ticket.SessionNr
        // Making a place free
        session.cinemaHall[ticket.Row][ticket.Column] = 0
        // Removing the ticket from list
        tickets.removeIf { it.TikcetNr == ticketId }
        return true
    }

}