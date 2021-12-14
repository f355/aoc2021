import kotlin.math.max
import kotlin.math.min

class Aoc14(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true), 10))
        println(solve(readFile(false), 10))
        println(solve(readFile(true), 40))
        println(solve(readFile(false), 40))
    }

    private lateinit var mappings: MutableMap<Pair<Char, Char>, Char>
    private lateinit var counts: MutableMap<Char, Long>
    private lateinit var pairCounts: MutableMap<Pair<Char, Char>, Long>

    private fun solve(input: List<String>, iterations: Int): Long {
        mappings = mutableMapOf()
        val template = input[0].toList()
        input.drop(1).forEach {
            val (pair, to) = it.split(" -> ")
            mappings[Pair(pair[0], pair[1])] = to[0]
        }
        counts =
            template.groupingBy { it }.eachCount().map { (k, v) -> Pair(k, v.toLong()) }.toMap()
                .toMutableMap()
        pairCounts = template.windowed(2)
            .groupingBy { (a, b) -> Pair(a, b) }
            .eachCount()
            .map { (k, v) -> Pair(k, v.toLong()) }
            .toMap().toMutableMap()

        repeat(iterations) {
            val newPairCounts = mutableMapOf<Pair<Char, Char>, Long>()
            pairCounts.forEach { (p, cnt) ->
                val (a, b) = p
                val ins = mappings[p]!!
                newPairCounts.compute(Pair(a, ins)) { _, prev -> (prev ?: 0) + cnt }
                newPairCounts.compute(Pair(ins, b)) { _, prev -> (prev ?: 0) + cnt }
                counts.compute(ins) { _, prev -> (prev ?: 0) + cnt }
            }
            pairCounts = newPairCounts
        }

        return counts.toList()
            .fold(Pair(Long.MAX_VALUE, Long.MIN_VALUE)) { (mmin, mmax), (_, x) ->
                Pair(min(mmin, x), max(mmax, x))
            }.run { second - first }
    }
}
