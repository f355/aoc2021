class Aoc01(day: String) : Aoc(day) {
    override fun run() {
        println(solve1(readFile(true)))
        println(solve1(readFile(false)))
        println(solve2(readFile(true)))
        println(solve2(readFile(false)))
    }

    private fun solve1(input: List<String>) =
        input.map { it.toInt() }
            .fold(Pair(-1, 0)) { (cnt, prev), curr ->
                Pair(if (curr > prev) cnt + 1 else cnt, curr)
            }
            .first

    private fun solve2(input: List<String>) : Int {
        val ins = input.map { it.toInt() }.toIntArray()
        var cnt = 0
        var window = ins[0] + ins[1] + ins[2]
        for (i in 3 until ins.size) {
            val newWindow = window - ins[i-3] + ins[i]
            if (newWindow > window) cnt++
            window = newWindow
        }
        return cnt
    }
}
