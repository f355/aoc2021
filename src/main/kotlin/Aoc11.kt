class Aoc11(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
    }

    private lateinit var m: Array<IntArray>

    private fun solve(input: List<String>): Pair<Int, Int> {
        m = Array(input.size + 2) { IntArray(input[0].length + 2) { 0 } }
        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                m[i + 1][j + 1] = c.digitToInt()
            }
        }
        var totalFlashes = 0
        var res1st = 0
        var res2nd = 0
        var step = 0
        //printM()
        while (true) {
            val toFlash = mutableListOf<Pair<Int, Int>>()
            for (i in 1 until m.size - 1)
                for (j in 1 until m[0].size - 1) {
                    (m[i][j]++).also {
                        if (it >= 9) toFlash.add(Pair(i, j))
                    }
                }
            var flashes = 0
            toFlash.forEach { (i, j) -> flashes += flash(i, j) }
            totalFlashes += flashes
            if (step == 99) res1st = totalFlashes
            if (flashes == (m.size - 2) * (m[0].size - 2)) {
                res2nd = step + 1
            }
            if (res1st != 0 && res2nd != 0) {
                return Pair(res1st, res2nd)
            }
            // println(toFlash)
            // printM()
            step++
        }
    }

    private fun flash(x: Int, y: Int): Int {
        var res = 0
        if (m[x][y] != 0) {
            m[x][y]++
        }
        if (m[x][y] > 9) {
            res++
            m[x][y] = 0
            for (i in x-1..x+1) {
                for (j in y-1..y+1) {
                    res += flash(i, j)
                }
            }
        }
        return res
    }

    private fun printM() {
        m.forEach { row ->
            row.forEach {
                print(it)
            }
            println()
        }
        println()
    }

}
