class Aoc15(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
    }

    private lateinit var m: Array<IntArray>
    private lateinit var risks: Array<IntArray>

    private fun solve(input: List<String>): Pair<Int, Int> {
        m = Array(input.size) { IntArray(input[0].length) { 0 } }
        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                m[i][j] = c.digitToInt()
            }
        }
        val res1 = solve1()

        val m1 = Array(m.size * 5) { IntArray(m[0].size * 5) { 0 } }
        for (i in 0..4) {
            for (j in 0..4) {
                for (x in m.indices) {
                    for (y in m.indices) {
                        val x1 = x + m.size * i
                        val y1 = y + m[0].size * j
                        val v = m[x][y] + i + j
                        m1[x1][y1] = if (v > 9) v.mod(9) else v
                    }
                }
            }
        }
        m = m1
        val res2 = solve1()
        return Pair(res1, res2)
    }

    private fun solve1(): Int {
        risks = Array(m.size) { IntArray(m[0].size) { Int.MAX_VALUE } }
        risks[0][0] = 0
        var next = neighbors(0, 0)
        while (next.isNotEmpty()) {
            next = next.flatMap { neighbors(it.first, it.second) }
        }
        return risks.last().last()
    }

    private fun neighbors(x: Int, y: Int): List<Pair<Int, Int>> {
        val risk = risks[x][y]
        return listOfNotNull(
            updateAdjacent(x - 1, y, risk),
            updateAdjacent(x + 1, y, risk),
            updateAdjacent(x, y - 1, risk),
            updateAdjacent(x, y + 1, risk)
        ).sortedBy { it.third }.map { (x1, y1, _) -> Pair(x1, y1) }
    }

    private fun updateAdjacent(x: Int, y: Int, risk: Int): Triple<Int, Int, Int>? {
        if (x in m.indices && y in m[0].indices) {
            val newRisk = risk + m[x][y]
            if (newRisk < risks[x][y]) {
                risks[x][y] = newRisk
                return Triple(x, y, newRisk)
            }
        }
        return null
    }
}
