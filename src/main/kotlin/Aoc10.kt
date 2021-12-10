import java.util.Stack

class Aoc10(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
    }

    private fun solve(input: List<String>): Pair<Int, Long> {
        val chars = mapOf(
            ')' to Pair('(', 3),
            ']' to Pair('[', 57),
            '}' to Pair('{', 1197),
            '>' to Pair('<', 25137)
        )
        val chars2nd = mapOf(
            '(' to 1,
            '[' to 2,
            '{' to 3,
            '<' to 4
        )
        var res = 0
        val scores2nd = mutableListOf<Long>()
        input.forEach { row ->
            val stack = Stack<Char>()
            var broken = false
            for (c in row) {
                if (c !in chars.keys) {
                    stack.push(c)
                } else {
                    val o = stack.pop()
                    val (exp, score) = chars[c]!!
                    if (exp != o) {
                        res += score
                        broken = true
                        break
                    }
                }
            }
            if (!stack.empty() && !broken) {
                var score = 0L
                while (!stack.empty()) {
                    val c = stack.pop()
                    val cs = chars2nd[c]!!
                    score = score * 5 + cs
                }
                scores2nd.add(score)
            }
        }
        val res2nd = scores2nd.sorted()[(scores2nd.size / 2)]
        return Pair(res, res2nd)
    }

}
