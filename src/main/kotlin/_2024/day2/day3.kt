package _2024.day2

import util.parseIntList
import util.readInputLines

fun solvePart1(input: List<String>): Int =
    input.map { report ->
        report.parseIntList()
            .windowed(3)
            .map { isCurrentNumSafe(it) }
            .all { it }
    }.count { it }

fun solvePart2(input: List<String>): Int =
    input.map { report ->
        report.parseIntList()
            .let { permutations(it) }
            .any { permutation -> permutation.windowed(3).map { isCurrentNumSafe(it)}.all { it } }
    }.count { it }

private fun isCurrentNumSafe(window: List<Int>): Boolean {
    val (before, current, next) = window
    val beforeDiff = before - current
    val nextDiff = current - next
    return safeDifference(beforeDiff) and
            safeDifference(nextDiff) and
            (beforeDiff < 0 == nextDiff < 0)
}

private fun safeDifference(difference: Int) = Math.abs(difference) in (1..3)

fun permutations(nums:List<Int>) =
    sequence {
        nums.forEachIndexed { i, _ ->
            yield(nums.filterIndexed { index, _ -> index != i })
        }
    }.toList()


fun main() {
    solvePart1(
        readInputLines("_2024/day2_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2024/day2_part1")
    )
        .also { println("Part two result: $it") }
}