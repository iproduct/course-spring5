open class Shape

fun Shape.draw() {
    println("Inside draw()")
}

class Rectangle(var width: Double = 0.0 , var height: Double = 0.0): Shape() {
    val perimeter = (width + height) * 2
    override fun toString() = "Rectange($width, $height)"
}

data class Post(val id: Long, val title: String, val content: String)

fun main(args: Array<String>) {
//    println("What's your name?")
//    val name = readLine()
//    println("Hello $name")

    var x = 3
    x += 1
    fun incrementX() {
        x += 1
    }
    incrementX()
    incrementX()
    println("x = $x")

//    fun sum(a: Int, b: Int): Int {
//        return a + b
//    }
    fun sum(a: Int = 1, b: Int = 3) = a + b
//    val sum = {a: Int, b: Int -> a + b}
//    val sum = fun(a: Int, b: Int) = a + b
    println("sum(5, 7) = ${sum(b = 7)}")

    val sh1 = Shape()
    sh1.draw()

    val r1  = Rectangle(5.0, 8.0)
    println("Reactangle $r1 perimeter is ${r1.perimeter}")

    val posts = listOf<Post>(
        Post(1L, "New in Kotlin", "MMP is new ..."),
        Post(2L, "Kotlin compared with Java", "Many extaras added .."),
        Post(3L, "Coroutines and Channels in Kotlin", "Concurrent programming is made easy ..."),
    )

    posts.filter{ it.title.contains("Kotlin")}
        .map { "${it.id}: ${it.title} - ${it.content}" }
        .forEach { println(it) }

}



