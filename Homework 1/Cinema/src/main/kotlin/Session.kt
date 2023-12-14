import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat

/**
 * A class that contains information about movie session
 */
@Serializable
class Session(var id : Int, var movie : Movie, var time : String) {
    // Scheme of cinema hall, 0 means that place[i][j] is currently free
    val cinemaHall = Array(SIZE) { Array(SIZE) { 0 } }


    fun showPlaces() {
        for (i in 0..<SIZE) {
            for (j in 0..<SIZE) {
                print("${cinemaHall[i][j]} ")
            }
            println()
        }
    }
}