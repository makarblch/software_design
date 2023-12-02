fun main(args: Array<String>) {
    val n : Int = readln().toInt()
    val table: Array<Array<Int>> = Array(n) { Array(n, {0}) }
    // Заполнение и вывод
    for (i in 0..<n) {
        for (j in 0..<n) {
            table[i][j] = (0..9).random()
            print("${table[i][j]} ")
        }
        println()
    }
    // Подсчет суммы
    var sum : Int = 0
    for (i in 0..<n) {
        for (j in n-i..<n) {
            sum += table[i][j]
        }
    }
    println("Сумма равна : ${sum}")
}