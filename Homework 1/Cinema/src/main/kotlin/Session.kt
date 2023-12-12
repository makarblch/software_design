import java.time.LocalDateTime

/**
 * A class that contains information about movie session
 */
class Session(id : Int, movie : Movie, time : LocalDateTime) {
    private var id : Int
    private var movie : Movie
    private var time : LocalDateTime
    // Scheme of cinema hall, 0 means that place[i][j] is currently free
    val cinemaHall = Array(SIZE) { Array(SIZE) { 0 } }

    val Id : Int
        get() = id
    var Movie : Movie
        get() = movie
        set(value) {
            movie = value
        }

    var Time : LocalDateTime
        get() = time
        set(value) {
            time = value
        }

    init {
        this.movie = movie
        this.time = time
        this.id = id
    }

    fun showPlaces() {
        for (i in 0..<SIZE) {
            for (j in 0..<SIZE) {
                print("${cinemaHall[i][j]} ")
            }
            println()
        }
    }
}