import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

object Serializer {
    /**
     * Deserializes files with movies, sessions and tickets
     */
    fun deserialize(system : System) {
        try {
            var read = File("movies.json").readText(Charsets.UTF_8)
            if (read != "") {
                system.movies = Json.decodeFromString<MutableList<Movie>>(read)
            }
            read = File("sessions.json").readText(Charsets.UTF_8)
            if (read != "") {
                system.sessions = Json.decodeFromString<MutableList<Session>>(read)
            }
            read = File("tickets.json").readText(Charsets.UTF_8)
            if (read != "") {
                system.tickets = Json.decodeFromString<MutableList<Ticket>>(read)
            }
        } catch (ex : IOException) {
            println("Unable to read files :(")
        } catch (_ : Exception) {
            println("Ooops! Something went wrong!")
        }
    }

    /**
     *  Deserializes movies, sessions and tickets to files
     */
    fun serialize(system : System) {
        try {
            if (system.movies.size != 0) {
                File("movies.json").writeText(Json.encodeToString<MutableList<Movie>>(system.movies))
            } else {
                File("movies.json").writeText("")
            }
            if (system.sessions.size != 0) {
                File("sessions.json").writeText(Json.encodeToString<MutableList<Session>>(system.sessions))
            } else {
                File("sessions.json").writeText("")
            }
            if (system.tickets.size != 0) {
                File("tickets.json").writeText(Json.encodeToString<MutableList<Ticket>>(system.tickets))
            } else {
                File("tickets.json").writeText("")
            }
        } catch (ex : IOException) {
            println("Unable to write files :(")
        } catch (_ : Exception) {
            println("Ooops! Something went wrong!")
        }
    }

    /**
     * Creates files with movies, sessions and tickets at the beginning
     */
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

}