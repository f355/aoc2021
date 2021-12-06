class Aoc06(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
        println(solve(readFile(true), 256))
        println(solve(readFile(false), 256))
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
