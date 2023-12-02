import kotlin.math.pow

abstract class Figure(val base : Int, val height : Int){
    abstract fun volume() : Double
    abstract fun projectionOxy() : Double
    abstract fun projectionOyz() : Double
}

class Pyramid(base : Int, height : Int) : Figure(base, height) {
    override fun volume(): Double {
        return base * base * height / 3.0
    }

    override fun projectionOxy(): Double {
        return (base * base).toDouble()
    }

    override fun projectionOyz(): Double {
        return base * height / 2.0
    }
}

class Cube(base : Int, height : Int) : Figure(base, height) {
    override fun volume(): Double {
        return (base * base * height).toDouble()
    }

    override fun projectionOxy(): Double {
        return (base * base).toDouble()
    }

    override fun projectionOyz(): Double {
        return (base * height).toDouble()
    }
}

class Cone(base : Int, height : Int) : Figure(base, height) {
    override fun volume(): Double {
        return Math.PI * Math.pow(base.toDouble(), 2.0) * height / 12.0
    }

    override fun projectionOxy(): Double {
        return Math.PI * Math.pow(base.toDouble(), 2.0) / 4.0
    }

    override fun projectionOyz(): Double {
        return base * height / 2.0
    }
}

fun main(args: Array<String>) {
    print("Введите тип фигуры (Пирамида/Куб/Конус): ")
    val figure = readln().lowercase();
    print("Введите основание фигуры: ")
    val base = readln().toInt();
    print("Введите высоту фигуры: ")
    val height = readln().toInt();

    val figs : Figure = when (figure) {
        "пирамида" -> Pyramid(base, height)
        "конус" -> Cone(base, height)
         else -> Cube(base, height)
    }

    println("Объем равен: ${figs.volume()}")
    println("Проекция на плоскость ОХУ равна: ${figs.projectionOxy()}")
    println("Проекция на плоскость ОУZ равна: ${figs.projectionOyz()}")
}