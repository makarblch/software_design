import kotlinx.serialization.Serializable

@Serializable
class Good(val title : String, var price : Int, var count : Int) {
    override fun toString(): String {
        return "$title, price = $price; "
    }

}