abstract class Aoc {
    abstract fun run()

    protected fun readFile(task: String, trivial: Boolean): List<String> {
        val suffix = if (trivial) "_trivial" else ""
        return Aoc::class.java.getResource("input$task$suffix.txt")!!.readText()
            .split("\n")
            .filter { it.isNotEmpty() }
    }
}
