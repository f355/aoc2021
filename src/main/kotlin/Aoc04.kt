class Aoc04 : Aoc() {
    override fun run() {
        println(solve(readFile("04", true, true), false))
        println(solve(readFile("04", false, true), false))
        println(solve(readFile("04", true, true), true))
        println(solve(readFile("04", false, true), true))
    }

    private fun solve(input: List<String>, last: Boolean): Int {
        val game = input.first().split(",").map { it.toInt() }
        val boards = mutableListOf<Array<IntArray>>()
        val genBoard = { Array(5) { IntArray(5) { 0 } } }
        var board = genBoard()
        var ri = 0
        for (i in 2 until input.size) {
            if (input[i].isEmpty()) {
                boards.add(board)
                board = genBoard()
                ri = 0
                continue
            }
            val row = input[i].chunked(3) { it.trim().toString().toInt() }.toIntArray()
            board[ri] = row
            ri++
        }
        var winnerScore = 0
        game.forEach { n ->
            boards.removeAll { board ->
                var sum = 0
                for (i in board.indices) {
                    for (j in board[i].indices) {
                        when(board[i][j]) {
                            n -> board[i][j] = -1
                            -1 -> {}
                            else -> sum += board[i][j]
                        }
                    }
                }
                if (isWinner(board)) {
                    winnerScore = sum * n
                    true
                } else
                    false
            }
            if (!last && winnerScore != 0) return winnerScore
        }
        return winnerScore
    }

    private fun isWinner(board: Array<IntArray>): Boolean {
        for (i in board.indices) {
            if (board[i].all { it == -1 } || board.map { it[i] }.all { it == -1 }) {
                return true
            }
        }
        return false
    }
}
