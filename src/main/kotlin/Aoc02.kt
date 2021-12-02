class Aoc02 : Aoc() {
    override fun run() {
        println(solve1(readFile("02", true)))
        println(solve1(readFile("02", false)))
        println(solve2(readFile("02", true)))
        println(solve2(readFile("02", false)))
    }

    private fun solve1(input: List<String>): Int {
        var hPos = 0
        var depth = 0
        input.forEach {
            val (instr, sArg) = it.split(" ")
            val arg = sArg.toInt()
            when (instr) {
                "forward" -> hPos += arg
                "down" -> depth += arg
                "up" -> depth -= arg
            }
        }
        return hPos * depth
    }

    private fun solve2(input: List<String>) : Int {
        var hPos = 0
        var depth = 0
        var aim = 0
        input.forEach {
            val (instr, sArg) = it.split(" ")
            val arg = sArg.toInt()
            when (instr) {
                "forward" -> {
                    hPos += arg
                    depth += aim * arg
                }
                "down" -> aim += arg
                "up" -> aim -= arg
            }
        }
        return hPos * depth
    }
}
