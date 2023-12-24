import kotlinx.serialization.Serializable

/**
 * A class that contains information about movie session
 */
@Serializable
class Session(var id: Int, var movie: Movie, var time: String) {

    // Scheme of cinema hall, 0 means that place[i][j] is currently free
    val cinemaHall = Array(SIZE) { Array(SIZE) { 0 } }

    val sessionVisitors = Array(SIZE) { Array(SIZE) { 0 } }


    fun showPlaces() {
        println("_______Screen_______")
        println("   0 1 2 3 4 5 6 7 8 9")
        println("   _ _ _ _ _ _ _ _ _ _")
        for (i in 0..<SIZE) {
            print("$i |")
            for (j in 0..<SIZE) {
                print("${cinemaHall[i][j]} ")
            }
            println()
        }
    }

    override fun toString(): String {
        return "Session of $movie, $time "
    }
}