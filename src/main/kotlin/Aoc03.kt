class Aoc03 : Aoc() {
    override fun run() {
        println(solve1(readFile("03", true)))
        println(solve1(readFile("03", false)))
        println(solve2(readFile("03", true)))
        println(solve2(readFile("03", false)))
    }

    private fun solve1(input: List<String>): Int {
        val inp = input.map { l -> l.map { it == '1' }.toBooleanArray() }
        val width = inp[0].size
        val sums = IntArray(width)
        inp.forEach {
            it.forEachIndexed { i, v -> if (v) sums[i]++ else sums[i]-- }
        }
        var g = 0
        var e = 0
        sums.forEachIndexed { i, v ->
            when {
                v > 0 -> g = g or (1 shl (width - i - 1))
                v < 0 -> e = e or (1 shl (width - i - 1))
                else -> {
                    throw RuntimeException("ambiguous digit $i")
                }
            }
        }
        return g * e
    }

    private fun solve2(input: List<String>) : Int {
        val inp = input.map { l -> l.map { it == '1' }.toBooleanArray() }
        return filterDigit(inp, false) * filterDigit(inp, true)
    }

    private fun filterDigit(inp: List<BooleanArray>, least: Boolean): Int {
        var l = inp
        for (i in 0 until l[0].size ) {
            val sum = l.fold(0) { acc, row ->
                if (row[i]) acc + 1 else acc - 1
            }

            l = l.filter { it[i] == (sum >= 0) xor least }
            if (l.size == 1) {
                return l[0].fold(0) { acc, d ->
                    (acc shl 1) + if (d) 1 else 0
                }
            }
        }
        return 0
    }
}
