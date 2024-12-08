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
        if (turnAt == i && currentPoint.step().point in grid) {
            if (grid[currentPoint.step().point] == "#")
                grid[currentPoint.turnRight().step().point] = "O"
            else
                grid[currentPoint.step().point] = "O"
        }
        while (grid[currentPoint.step().point] in listOf("#", "O")) currentPoint = currentPoint.turnRight()
        currentPoint = currentPoint.step()
        if (currentPoint.point !in grid) break
        if (grid[currentPoint.point] in listOf(currentPoint.direction.presentation())) throw CycleDetected(
            "Cycle at ${i}"
        )
        if (grid[currentPoint.point] in listOf("↑", "→", "↓", "←")) grid[currentPoint.point] = "X"
        if (grid[currentPoint.point] in listOf(".", "^")) {
            grid[currentPoint.point] = currentPoint.direction.presentation()
            yield(currentPoint)
        }
        i++
    }
}

fun solvePart2(input: List<String>): Int {
    val gridInit = Grid(input)
    val size = walkSequence(gridInit).count()
    gridInit.print()
    print("\n")

    //Size *2 is just because the count is not good enough. Crosses are only counted once
    return (0 until size * 2).count {
        val grid = Grid(input)
        try {
            walkSequence(grid, it).count()
            false
        } catch (e: CycleDetected) {
            println("Cycle at index $it")
            grid.print()
            print("\n")
            true
        }

    }
}

class CycleDetected(msg: String) : Exception(msg) {

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
    println(" - The following are wrong: 1910, 1920, 2013, 2546, 2635")
}
