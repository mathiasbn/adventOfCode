package _2024.day6

import util.*

fun solvePart1(input: List<String>): Int {
    val grid = Grid(input)
    return walkSequence(grid).count()
}

fun findStart(grid: Grid): DirectedPoint {
    val (point, _) = grid.rowIterator().first { it.value == "^" }
    return DirectedPoint(point, Direction.U)
}

fun walkSequence(grid: Grid, turnAt: Int? = null): Sequence<DirectedPoint> = sequence {
    var currentPoint: DirectedPoint = findStart(grid)
    var i = 0
    while (true) {
        if (turnAt == i)
            currentPoint = currentPoint.turnRight()
        val suggestion = currentPoint.step()
        currentPoint =
            if (grid[suggestion.point] == "#")
                currentPoint.turnRight().step()
            else suggestion

        if (currentPoint.point !in grid) break
        if (grid[currentPoint.point] in listOf("X", currentPoint.direction.presentation())) throw IllegalStateException("Cycle")
        if (grid[currentPoint.point] in listOf("↑", "→", "↓", "←")) grid[currentPoint.point] = "X"
        if (grid[currentPoint.point] in listOf(".", "^")) {
            yield(currentPoint)
            grid[currentPoint.point] = currentPoint.direction.presentation()
        }
        i++
    }
}

fun solvePart2(input: List<String>): Int {
    val grid = Grid(input)
    val size = walkSequence(grid).count()

    //Size *2 is just because the count is not good enough. Crosses are only counted once
    return (0 until size*2).count {
        val grid = Grid(input)
        try {
            walkSequence(grid, it).last()
//            println("======== GOOD ========")
//            grid.print()
            false
        } catch (e: Exception) {
//            println("======== Bad ========")
//            grid.print()
            true
        }

    }
}


fun main() {
    solvePart1(
        readInputLines("_2024/day6_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2024/day6_part1")
    )
        .also { println("Part two result: $it") }
    println(" - The following are wrong: 1920, 2546")
}
