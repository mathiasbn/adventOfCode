import java.lang.Exception

private data class Move(val amount: Int, val from: Int, val to: Int)

fun topOfStackAfterReArrange(input: String): String {
    val arrangementStr = input.lines().filter { it.isNotBlank() }.takeWhile { !it.startsWith(" 1") }
    val arrangement = arrangementStr.map {
        it.windowed(4, 4, true)
            .map { if (it.isBlank()) "" else it.trim().replace("[", "").replace(Regex("\\]\\s*"), "") }
    }
        .let { listOfRows ->
            val noColumns = listOfRows[0].count()
            (0 until noColumns).map { i ->
                listOfRows.map { it[i] }.filter { it.isNotBlank() }.reversed().toMutableList()
            }.toMutableList()
        }
    val movesStr = input.lines().dropWhile { !it.startsWith(" 1") }.drop(2).filter { it.isNotBlank() }
    val moves = movesStr.map { moveStr ->
        val moveMatch = Regex("move (\\d+) from (\\d+) to (\\d+)\\s*").matchEntire(moveStr)
        moveMatch?.groupValues?.let { Move(it[1].toInt(), it[2].toInt(), it[3].toInt()) }
            ?: throw Exception("Could not parse move string: $moveStr")
    }
    println(moves)
    println(arrangement)
    for (move in moves) {
        arrangement[move.to-1].addAll(arrangement[move.from-1].takeLast(move.amount))
        arrangement[move.from-1] = arrangement[move.from-1].dropLast(move.amount).toMutableList()
        println("After move ($move), the arrangement looks like: \n$arrangement")
    }

    return arrangement.map { it.last() }.joinToString("")
}

fun main() {
    println("Top of stacks after rearrange: " + topOfStackAfterReArrange(readResourceFile("day5_input.txt")))

    //Part two result DCVTCVPCL
}