package _2024.day8

import util.Grid
import util.Point
import util.cartesianProduct
import util.readInputLines


fun solvePart1(input: List<String>): Int {
    val grid = Grid(input)
    return findSameFrequencyPairs(grid)
        .flatMap { it ->
            val vector = it.vector()
            listOf(it.point2.step(vector), it.point1.step(vector.reverseVector()))
        }
        .toSet()
        .filter { it in grid }
        .onEach { v -> grid[v] = "#" }
        .count()
        .also { grid.print() }
}

fun findSameFrequencyPairs(grid: Grid) = grid.rowIterator().groupBy { it.value }
    .filterKeys { it != "." }
    .mapValues { same -> same.value.map { it.point } }
    .values.flatMap {
        cartesianProduct(listOf(it, it))
    }
    .filter { (from, to) -> from != to }
    .map { (from, to) -> from undirectedLineTo to }
    .toSet()


fun solvePart2(input: List<String>): Int {
    val grid = Grid(input)
    return findSameFrequencyPairs(grid)
        .flatMap { line ->
            val vector = line.vector()
            (0..99).asSequence()
                .map { i ->
                    (0 until i).fold(line.point2) { acc, _ -> acc.step(vector) }
                }
                .takeWhile { it in grid }
                .toList() +
                    (1..100).asSequence()
                        .map { i ->
                            (0 until i).fold(line.point2) { acc, _ -> acc.step(vector.reverseVector()) }
                        }
                        .takeWhile { it in grid }
                        .toList() +
                    listOf(line.point1, line.point2)

        }
        .toSet()
        .filter { it in grid }
        .onEach { v -> grid[v] = "#" }
        .count()
        .also { grid.print() }
}


fun main() {
    solvePart1(
        readInputLines("_2024/day8_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2024/day8_part1")
    )
        .also { println("Part two result: $it") }
}
