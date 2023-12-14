import kotlinx.serialization.Serializable

/**
 * A class that contains information about sold ticket
 */
@Serializable
class Ticket(var ticketNr : Int, var session: Session, var row : Int, var column : Int) {

    override fun toString(): String {
        return "Ticket $ticketNr, movie : ${session.movie.name}\n; row $row, column $column"
    }
}