package _2025.day4

import util.Grid
import util.readInputLines

fun solvePart1(input: List<String>): Int {
    val grid = Grid(input)
    return grid.rowIterator()
        .filter { it.value == "@" }
        .count { grid.allNeighboursOf(it.point).count { neighbour -> neighbour.value == "@" } < 4 }
}

fun solvePart2(input: List<String>): Int {
    val grid = Grid(input)
    var removed = 0
    var lastRemoved: Int
    do {
        val removable = grid.rowIterator()
            .filter { it.value == "@" }
            .filter { grid.allNeighboursOf(it.point).count { neighbour -> neighbour.value == "@" } < 4 }
            .toList()
        lastRemoved = removable.count()
        removed += lastRemoved
        removable.forEach { grid[it.point] = "." }

    } while (lastRemoved > 0)
    return removed
}


fun main() {
    solvePart1(
        readInputLines("_2025/day4_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2025/day4_part1")
    )
        .also { println("Part two result: $it") }
}