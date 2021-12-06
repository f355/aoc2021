import java.lang.Integer.min
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.sign

class Aoc05(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
        println(solve(readFile(true), true))
        println(solve(readFile(false), true))
    }

    private fun solve(input: List<String>, withDiagonals: Boolean = false): Int {
        val res = mutableMapOf<Pair<Int, Int>, Int>()
        input.forEach { il ->
            val (xy1, xy2) = il.split(" -> ").map { xy ->
                val (x, y) = xy.split(",").map { it.toInt() }
                Pair(x, y)
            }
            when {
                xy1.first == xy2.first ->
                    (min(xy1.second, xy2.second)..max(xy1.second, xy2.second)).forEach { i ->
                        res.compute(Pair(xy1.first, i)) { _, prev -> (prev ?: 0) + 1 }
                    }
                xy1.second == xy2.second ->
                    (min(xy1.first, xy2.first)..max(xy1.first, xy2.first)).forEach { i ->
                        res.compute(Pair(i, xy1.second)) { _, prev -> (prev ?: 0) + 1 }
                    }
                withDiagonals -> {
                    val xinc = (xy2.first - xy1.first).sign
                    val yinc = (xy2.second - xy1.second).sign
                    var x = xy1.first
                    var y = xy1.second
                    repeat((x - xy2.first).absoluteValue + 1) {
                        res.compute(Pair(x, y)) { _, prev -> (prev ?: 0) + 1 }
                        x += xinc
                        y += yinc
                    }
                }
            }
        }
        return res.values.count { it > 1 }
    }
}
