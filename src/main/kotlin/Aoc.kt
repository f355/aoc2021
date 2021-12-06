abstract class Aoc(private val day: String) {
    abstract fun run()

    protected fun readFile(trivial: Boolean, keepNewlines: Boolean = false): List<String> {
        val suffix = if (trivial) "_trivial" else ""
        return Aoc::class.java.getResource("input$day$suffix.txt")!!.readText()
            .split("\n")
            .filter { keepNewlines || it.isNotEmpty() }
    }
}
