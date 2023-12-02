fun main(args: Array<String>) {
    val str: String = readln()
    println(str)
    val words = str.split(" ")
    for (i in 1..10) {
        println(words.last())
    }
}