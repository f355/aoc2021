class Aoc09(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
    }

    private lateinit var m: Array<IntArray>

    private fun solve(input: List<String>): Pair<Int, Int> {
        m = Array(input.size + 2) { IntArray(input[0].length + 2) { 9 } }
        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                m[i + 1][j + 1] = c.digitToInt()
            }
        }
        var res = 0
        val lows = mutableListOf<Pair<Int, Int>>()
        for (i in 1 until m.size) {
            for (j in 1 until m[i].size) {
                val v = m[i][j]
                if (m[i][j - 1] > v &&
                    m[i][j + 1] > v &&
                    m[i - 1][j] > v &&
                    m[i + 1][j] > v
                ) {
                    res += v + 1
                    lows.add(Pair(i, j))
                }
            }
        }
        val res2 = lows.map { flood(it.first, it.second) }
            .sorted()
            .reversed()
            .take(3)
            .reduce { a, b -> a * b }
        return Pair(res, res2)
    }

    private fun flood(x: Int, y: Int): Int {
        if (m[x][y] == 9) return 0
        m[x][y] = 9
        return flood(x, y - 1) + flood(x, y + 1) + flood(x - 1, y) + flood(x + 1, y) + 1
    }
}
