fun main(args: Array<String>) {
    // solution 1
    val set = setOf(*args)
    println(set.size)
    // solution 2
    var count : Int = 1
    val len : Int = args.size - 1
    for (i in 1..len) {
        if (args[i] != args[i - 1]) {
            ++count
        }
    }
    println(count)
}