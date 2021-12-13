import kotlin.math.max
import kotlin.math.min

class Aoc13(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
    }

    private lateinit var paper: MutableSet<Pair<Int, Int>>

    private fun solve(input: List<String>): Int {
        paper = mutableSetOf()
        val instr = mutableListOf<Pair<String, Int>>()
        input.forEach { r ->
            if (r.startsWith("fold along ")) {
                val (dir, lc) = r.removePrefix("fold along ").split("=")
                instr.add(Pair(dir, lc.toInt()))
            } else {
                val (x, y) = r.split(",").map { it.toInt() }
                paper.add(Pair(x, y))
            }
        }
        var res1 = 0
        instr.forEach { (dir, line) ->
            when (dir) {
                "x" -> foldX(line)
                "y" -> foldY(line)
            }
            if (res1 == 0) res1 = paper.size
        }

        showPaper()
        return res1
    }

    private fun showPaper() {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        paper.forEach { (x, y) ->
            minX = min(x, minX)
            maxX = max(x, maxX)
            minY = min(y, minY)
            maxY = max(y, maxY)
        }
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (Pair(x, y) in paper) print("█") else print(".")
            }
            println()
        }
    }

    private fun foldX(line: Int) {
        paper.toList().forEach { pt ->
            val (x, y) = pt
            if (x > line) {
                paper.add(Pair(line - (x - line), y))
            }
            if (x >= line) {
                paper.remove(pt)
            }
        }
    }

    private fun foldY(line: Int) {
        paper.toList().forEach { pt ->
            val (x, y) = pt
            if (y > line) {
                paper.add(Pair(x, line - (y - line)))
            }
            if (y >= line) {
                paper.remove(pt)
            }
        }
    }
}
