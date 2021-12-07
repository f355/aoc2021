import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

class Aoc07(day: String) : Aoc(day) {
    override fun run() {
        println(solve1(readFile(true)))
        println(solve1(readFile(false)))
        println(solve2(readFile(true)))
        println(solve2(readFile(false)))
    }

    private fun solve1(input: List<String>): Int {
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        val inp = input[0].split(",").map { s ->
            s.toInt().also {
                min = min(it, min)
                max = max(it, max)
            }
        }.map { it - min }.groupingBy { it }.eachCount().toSortedMap()
        var countLeft = 0
        var countRight = inp.values.sum()
        var fuelLeft = 0
        var fuelRight = inp.map { (p, n) -> (p + 1) * n }.sum()
        var minFuel = fuelRight
        var prevP = -1
        for ((curP, curN) in inp) {
            val dist = curP - prevP
            fuelLeft += dist * countLeft
            fuelRight -= dist * countRight
            countLeft += curN
            countRight -= curN
            prevP = curP
            val total = fuelLeft + fuelRight
            if (total > minFuel) {
                return minFuel
            } else {
                minFuel = total
            }
        }

        return minFuel
    }

    private fun solve2(input: List<String>): Int {
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        val inp = input[0].split(",").map { s ->
            s.toInt().also {
                min = min(it, min)
                max = max(it, max)
            }
        }.map { it - min }.groupingBy { it }.eachCount().toSortedMap()
        var minFuel = Int.MAX_VALUE
        // can be better than O(N^2)?
        for (curPos in 0..max-min) {
            var fuel = 0
            for ((crabPos, crabNum) in inp) {
                val dist = (crabPos - curPos).absoluteValue
                fuel += crabNum * dist * (dist + 1) / 2
            }
            minFuel = min(minFuel, fuel)
        }

        return minFuel
    }
}
