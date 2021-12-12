class Aoc12(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
        println(solve(readFile(true), false))
        println(solve(readFile(false), false))
    }

    private lateinit var edges: MutableMap<String, MutableList<String>>

    private fun solve(input: List<String>, onlyOnce: Boolean = true): Int {
        edges = mutableMapOf()
        input.forEach { edge ->
            val (a, b) = edge.split("-")
            edges.compute(a) { _, v -> (v ?: mutableListOf()).apply { add(b) } }
            edges.compute(b) { _, v -> (v ?: mutableListOf()).apply { add(a) } }
        }

        return walk(setOf(), onlyOnce, "start")
    }

    private fun walk(visited: Set<String>, doubleVisited: Boolean, current: String): Int {
        when {
            current == "end" -> return 1
            current == "start" && visited.isNotEmpty() -> return 0
        }
        val doubleVisitedNext = if (current.all { it.isLowerCase() } && current in visited) {
            if (doubleVisited) return 0
            true
        } else doubleVisited
        val next = visited + current
        return edges[current]!!.sumOf { walk(next, doubleVisitedNext, it) }
    }
}
