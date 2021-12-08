import kotlin.math.pow

class Aoc08(day: String) : Aoc(day) {
    override fun run() {
        println(solve1(readFile(true)))
        println(solve1(readFile(false)))
        println(solve2(readFile(true)))
        println(solve2(readFile(false)))
    }

    private fun solve1(input: List<String>): Int {
        return input.flatMap { l ->
            val (_, digitStr) = l.split(" | ")
            digitStr.split(" ").filter {
                it.length in listOf(2, 3, 4, 7)
            }
        }.count()
    }

    private fun solve2(input: List<String>): Int {
        return input.sumOf { l ->
            val (digitStr, numberStr) = l.split(" | ")
            decode(digitStr, numberStr)
        }
    }

    private fun decode(digitStr: String, numberStr: String): Int {
        val digitSets = digitStr.split(" ").map { it.toSet() }.toMutableSet()
        val numberSets = numberStr.split(" ").map { it.toSet() }
        val digits = Array<Set<Char>>(10) { setOf() }
        // find 1 4 7 8
        digitSets.removeIf {
            when (it.size) {
                2 -> {
                    digits[1] = it; true
                }
                3 -> {
                    digits[7] = it; true
                }
                4 -> {
                    digits[4] = it; true
                }
                7 -> {
                    digits[8] = it; true
                }
                else -> false
            }
        }
        // remaining 0 2 3 5 6 9
        digitSets.removeIf {
            when {
                // 3 is the only 5-seg one that contains right edge (1)
                it.size == 5 && it.containsAll(digits[1]) -> {
                    digits[3] = it
                    true
                }
                // 0 is the only 6-seg one that contains 1 and doesn't contain 4
                it.size == 6 && it.containsAll(digits[1]) && !it.containsAll(digits[4]) -> {
                    digits[0] = it
                    true
                }
                // 5 is the only 5-seg that contains (4 - 1)
                it.size == 5 && it.containsAll(digits[4].subtract(digits[1])) -> {
                    digits[5] = it
                    true
                }
                else -> false
            }
        }
        // remaining 2 6 9
        digitSets.removeIf {
            when {
                // 2 is 5 seg
                it.size == 5 -> digits[2] = it
                // 9 contains 1
                it.size == 6 && it.containsAll(digits[1]) -> digits[9] = it
                // 6 remains
                else -> digits[6] = it
            }
            true
        }
        val digitMap = digits.withIndex().associate { (i, s) ->
            Pair(s, i)
        }
        return numberSets.reversed().mapIndexed { i, s ->
            digitMap[s]!! * 10.0.pow(i).toInt()
        }.sum()
    }
}
