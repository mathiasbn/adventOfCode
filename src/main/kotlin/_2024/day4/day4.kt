package _2024.day4

import util.*

fun solvePart1(input: List<String>): Int {
    val grid = Grid(input)
    return grid
        .rowIterator().filter { it.value == "X" }
        .map { numOfXmasStartingFrom(it.point, grid) }
        .sum()
}

fun numOfXmasStartingFrom(xPoint: Point, grid: Grid): Int =
    Direction.entries.toTypedArray()
        .count { dir ->
            listOf("M", "A", "S")
                .fold(Pair(xPoint, true)) { (currentPoint, valid), letter ->
                    currentPoint.step(dir)
                        ?.let { newPoint -> Pair(newPoint, valid && grid[newPoint] == letter) }
                        ?: Pair(currentPoint, false)
                }.second
        }

fun solvePart2(input: List<String>): Int {
    val grid = Grid(input)
    return grid
        .rowIterator().filter { it.value == "A" }
        .count { isMAScross(it.point, grid) }
}

fun isMAScross(point: Point, grid: Grid): Boolean {
    fun step(direction: Direction) = point.step(direction)?.let { grid[it] }

    val lf = step(Direction.UL) == "M" && step(Direction.DR) == "S" ||
            step(Direction.UL) == "S" && step(Direction.DR) == "M"
    val rl = step(Direction.UR) == "M" && step(Direction.DL) == "S" ||
            step(Direction.UR) == "S" && step(Direction.DL) == "M"
    return lf && rl
}


fun main() {
    solvePart1(
        readInputLines("_2024/day4_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2024/day4_part1")
    )
        .also { println("Part two result: $it") }
}