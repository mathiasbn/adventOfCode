import RpsMove.*
import RpsStrategy.*

fun parseRpsStrategy(strategy: String) = when (strategy) {
    "X" -> Lose; "Y" -> Draw; else -> Win
}

enum class RpsStrategy { Lose, Draw, Win }
enum class RpsMove(val moveValue: Int) {
    Rock(1), Paper(2), Scissor(3);

    fun scoresAgainst(opponentMove: RpsMove): Int = when (this) {
        Rock -> if (opponentMove == Scissor) 6 else if (opponentMove == Rock) 3 else 0
        Paper -> if (opponentMove == Rock) 6 else if (opponentMove == Paper) 3 else 0
        Scissor -> if (opponentMove == Paper) 6 else if (opponentMove == Scissor) 3 else 0
    }
}

fun moveFromABC(move: String) = if (move == "A") Rock else if (move == "B") Paper else Scissor
fun moveFromXYZ(move: String) = if (move == "X") Rock else if (move == "Y") Paper else Scissor
fun moveFromOpponentAndXYZ(opponentMove: RpsMove, strategy: RpsStrategy): RpsMove =
    when (opponentMove) {
        Rock -> when (strategy) {
            Lose -> Scissor
            Draw -> Rock
            Win -> Paper
        }
        Paper -> when (strategy) {
            Lose -> Rock
            Draw -> Paper
            Win -> Scissor
        }
        Scissor -> when (strategy) {
            Lose -> Paper
            Draw -> Scissor
            Win -> Rock
        }
    }

private fun parseRound(it: List<String>, xyzAsAMove: Boolean): Round {
    val opponentMove = moveFromABC(it[0])
    val yourMove =
        if (xyzAsAMove)
            moveFromXYZ(it[1])
        else
            moveFromOpponentAndXYZ(opponentMove, parseRpsStrategy(it[1]))
    return Round(opponentMove, yourMove)
}

data class Round(val opponentMove: RpsMove, val yourMove: RpsMove) {
    fun score() = yourMove.moveValue + yourMove.scoresAgainst(opponentMove)
}

fun rpsScore(input: String, xyzAsAMove: Boolean = true): Int {
    return input.lines()
        .asSequence()
        .filter { it.isNotBlank() }
        .map { it.split(" ") }
        .map { parseRound(it, xyzAsAMove) }
        .map { it.score() }
        .sum()
}

fun main() {
    println("Your total score: " + rpsScore(readResourceFile("day2_input.txt")))
    println("Your total score, where XYZ is strategy: " + rpsScore(readResourceFile("day2_input.txt"), xyzAsAMove = false))
}