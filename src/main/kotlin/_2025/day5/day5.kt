package _2025.day5

import util.merge
import util.overlaps
import util.readInputLines
import util.size

fun List<String>.freshRanges() = takeWhile { it.isNotEmpty() }.map { it.split("-").let { it[0].toLong()..it[1].toLong() } }

fun solvePart1(input: List<String>): Int {
    val freshRanges = input.freshRanges()
    val ingredients = input.takeLastWhile { it != "" }.map { it.toLong() }
    return ingredients.count { ingredient -> freshRanges.any { freshRange -> freshRange.contains(ingredient) } }
}

fun solvePart2(input: List<String>): Long {
    return input.freshRanges().fold(listOf<LongRange>()) { distinctRanges, range ->
        val untouchedRanges = distinctRanges.filter { !it.overlaps(range) }
        val overlappingMerged = (listOf(range) + distinctRanges.filter { it.overlaps(range) })
            .reduce { acc, longs -> acc.merge(longs) }
        untouchedRanges.plusElement(overlappingMerged)
    }.sumOf { it.size() }
}


fun main() {
    solvePart1(
        readInputLines("_2025/day5_part1", allowEmptyLines = true)
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2025/day5_part1", allowEmptyLines = true)
    )
        .also { println("Part two result: $it") }
}