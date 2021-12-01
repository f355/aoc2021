fun main(args: Array<String>) {
    println("Hello World!")

    val clazz = Class.forName("Aoc${args[0]}").getConstructor().newInstance() as Aoc
    clazz.run()
}
