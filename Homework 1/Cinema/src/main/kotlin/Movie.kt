/**
 * A class that contains information about current movies
 * @param name name of film
 * @param director last name of director
 * @param duration duration of the film in minutes
 */
class Movie (name : String, director : String, duration : Int) {
    private var name : String
    private var director : String
    private var duration : Int

    var Name : String
        get() = name
        set(value) {
            name = value
        }

    var Director : String
        get() = director
        set(value) {
            director = value
        }

    var Duration : Int
        get() = duration
        set(value) {
            duration = value
        }

    init {
        this.name = name
        this.director = director
        this.duration = duration
    }

    override fun toString(): String {
        return "Movie $name"
    }
}