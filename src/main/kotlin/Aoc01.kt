class Aoc01 : Aoc() {
    override fun run() {
        println(solve1(readFile("01", true)))
        println(solve1(readFile("01", false)))
        println(solve2(readFile("01", true)))
        println(solve2(readFile("01", false)))
    }

    private fun solve1(input: List<String>) =
        input.map { it.toInt() }
            .fold(Pair(-1, 0)) { (cnt, prev), curr ->
                Pair(if (curr > prev) cnt + 1 else cnt, curr)
            }
            .first

    private fun solve2(input: List<String>) {
        val ins = input.map { it.toInt() }.toIntArray()
        var cnt = 0
        var window = ins[0] + ins[1] + ins[2]
        for (i in 3 until ins.size) {
            val newWindow = window - ins[i-3] + ins[i]
            if (newWindow > window) cnt++
            window = newWindow
        }
        println(cnt)
    }
}
