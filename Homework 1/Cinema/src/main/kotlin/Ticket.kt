/**
 * A class that contains information about sold ticket
 */
class Ticket(ticketNr : Int, session: Session, row : Int, column : Int) {
    private var ticketNr : Int
    private var session : Session
    private var row : Int
    private var column : Int

    init {
        this.ticketNr = ticketNr
        this.session = session
        this.row = row
        this.column = column
    }

    var TikcetNr : Int
        get() = ticketNr
        set(value) {
            ticketNr = value
        }

    var SessionNr : Session
        get() = session
        set(value) {
            session = value
        }

    var Row : Int
        get() = row
        set(value) {
            row = value
        }

    var Column : Int
        get() = column
        set(value) {
            column = value
        }
}