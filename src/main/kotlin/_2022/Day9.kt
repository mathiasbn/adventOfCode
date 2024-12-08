package moinz.day9

import util.Point
import moinz.day9.Direction.*
import readResourceFile
import kotlin.math.abs

enum class Direction { Up, Down, Left, Right }
data class Move(val direction: Direction, val distance: Int)
data class HeadToTail(val points: List<Point>)

tailrec fun move(headTail: HeadToTail, tailPositions: Set<Point>, move: Move): Pair<HeadToTail, Set<Point>> {
    if (move.distance == 0) return Pair(headTail, tailPositions)

    val newHead = when (move.direction) {
        Up -> Point(headTail.points.first().x, headTail.points.first().y + 1)
        Down -> Point(headTail.points.first().x, headTail.points.first().y - 1)
        Left -> Point(headTail.points.first().x - 1, headTail.points.first().y)
        Right -> Point(headTail.points.first().x + 1, headTail.points.first().y)
    }
    var prev = newHead
    val newHeadToTail = HeadToTail(listOf(newHead) + headTail.points.drop(1).mapIndexed { i, it ->
        (
                if (abs(it.x - prev.x) > 1 ||
                    abs(it.y - prev.y) > 1
                )
                    Point(
                        if (it.x < prev.x) it.x + 1
                        else if (prev.x < it.x) it.x - 1
                        else it.x,
                        if (it.y < prev.y) it.y + 1
                        else if (prev.y < it.y) it.y - 1
                        else it.y,
                    )
                else it
                )
            .also { prev = it }/*.also { newPos -> println("${i + 1} --- $it $move $newPos") }*/
    })
    return move(newHeadToTail, tailPositions + newHeadToTail.points.last(), move.copy(distance = move.distance - 1))
}

fun countTailPositions(input: String, knotCount: Int = 2): Int {
    val moves = parse(input)
    val (_, tailPoints) = moves.execute(HeadToTail((1..knotCount).map { Point(0, 0) }))

    val minX = tailPoints.map { it.x }.min()-5
    val maxX = tailPoints.map { it.x }.max()+5
    val minY = tailPoints.map { it.y }.min()-5
    val maxY = tailPoints.map { it.y }.max()+5
    (minY..maxY).reversed().forEach { y ->
        (minX..maxX).map { x -> if (tailPoints.contains(Point(x, y))) "#" else "." }
            .also { println(y.toString().padStart(3) + it.joinToString("")) }
    }
    Thread.sleep(1000)
    return tailPoints.size
}

private fun List<Move>.execute(knots: HeadToTail) =
    fold(Pair(knots, setOf<Point>())) { acc, move -> move(acc.first, acc.second, move) }

private fun parse(input: String) = input.lines().filter { it.isNotBlank() }
    .map {
        it.split(" ").let {
            Move(
                when (it[0]) {
                    "U" -> Up; "D" -> Down; "L" -> Left; "R" -> Right; else -> throw Exception()
                }, it[1].toInt()
            )
        }
    }

fun main() {
    println("The amount of different positions of tail: " + countTailPositions(readResourceFile("day9_input.txt")))
    println(
        "The amount of different positions of tail given 10 knots: " +
                countTailPositions(readResourceFile("day9_input.txt"), knotCount = 10)
    )
}