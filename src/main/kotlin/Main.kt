fun main(args: Array<String>) {
    val clazz = Class.forName("Aoc${args[0]}")
        .getConstructor(String::class.java)
        .newInstance(args[0]) as Aoc
    clazz.run()
}
