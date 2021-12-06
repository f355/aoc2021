import java.lang.Integer.min
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.sign

class Aoc06 : Aoc() {
    override fun run() {
        println(solve(readFile("06", true)))
        println(solve(readFile("06", false)))
        println(solve(readFile("06", true), 256))
        println(solve(readFile("06", false), 256))
    }

    private fun solve(input: List<String>, days: Int = 80): Long {
        val ages = LongArray(9) { 0 }
        input[0].split(",").forEach {
            ages[it.toInt()]++
        }
        repeat(days) {
            val births = ages[0]
            for (age in 0..7) {
                ages[age] = ages[age + 1]
            }
            ages[8] = births
            ages[6] += births
        }
        return ages.sum()
    }
}
