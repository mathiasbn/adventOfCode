package _2025.day6

import _2025.day6.Operator.ADD
import _2025.day6.Operator.MUL
import util.Grid
import util.Point
import util.readInputLines

enum class Operator { ADD, MUL }
data class MathProblem(val numbers: List<Long>, val operator: Operator) {
    fun solve(): Long {
        return when (operator) {
            ADD -> numbers.reduce { a, b -> a.plus(b) }
            MUL -> numbers.reduce { a, b -> a.times(b) }
        }
    }
}

fun parseInputFor1(input: List<String>): List<MathProblem> {
    val lines = input.map { it.split(Regex("\\s+")).filter { it.isNotBlank() } }
    val numOfProblems = lines[0].size
    return (0..<numOfProblems).map { problemIndex ->
        lines.map { it[problemIndex] }
    }.map {
        MathProblem(
            it.dropLast(1).map { it.toLong() },
            parseOperator(it.last())
        )
    }
}

private fun parseOperator(cell: String): Operator = when (cell) {
    "*" -> MUL
    "+" -> ADD
    else -> throw Exception()
}


fun solvePart1(input: List<String>): Long =
    parseInputFor1(input).sumOf { it.solve() }

fun seperatorColumns(grid: Grid, numOfRows: Int) =
    grid.rows[0].mapIndexedNotNull { column, _ ->
        if (fullCollumnPoints(column, numOfRows)
                .map { grid.valueOf(it) }.all { it?.isBlank() ?: throw Exception("Point out of bounds") }
        )
            column
        else null
    }
        .plusElement(grid.rows[0].size)

private fun fullCollumnPoints(column: Int, numOfRows: Int): List<Point> =
    (0..<numOfRows).map { Point(column, it) }

fun solvePart2(input: List<String>): Long {
    val grid = Grid(input)
    val seperators = seperatorColumns(grid, input.size)
    val problemRanges = seperators.fold(Pair(0, listOf<IntRange>())) { (start, ranges), nextSeperator ->
        Pair(nextSeperator + 1, ranges.plusElement(start..<nextSeperator))
    }.second
    return problemRanges.map { range ->
        MathProblem(
            range.map { column ->
                fullCollumnPoints(column, input.size - 1).map { grid.valueOf(it)!! }.filter { it.isNotBlank() }
                    .joinToString("")
                    .toLong()
            },
            parseOperator(grid.valueOf(Point(range.first, input.size - 1))!!)
        )
    }.also(::println)
        .sumOf { it.solve() }
}


fun main() {
    solvePart1(
        readInputLines("_2025/day6_part1")
    )
        .also { println("Part one result: $it") }

    //Too low: 8342588838713
    solvePart2(
        readInputLines("_2025/day6_part1")
    )
        .also { println("Part two result: $it") }
}