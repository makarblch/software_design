import kotlinx.serialization.Serializable

/**
 * A class that contains information about current movies
 * @param name name of film
 * @param director last name of director
 * @param duration duration of the film in minutes
 */
@Serializable
class Movie (var name : String, var director : String, var duration : Int) {

    override fun toString(): String {
        return "Movie $name"
    }
}