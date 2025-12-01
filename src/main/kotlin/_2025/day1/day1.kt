package _2025.day1

import util.readInputLines
import kotlin.collections.last
import kotlin.collections.plus
import kotlin.math.absoluteValue

fun direction(rotation: String) = when (rotation[0]) {
    'L' -> -1
    'R' -> 1
    else -> throw IllegalStateException()
}

fun amount(rotation: String) = rotation.drop(1).toInt()

fun solvePart1(input: List<String>): Int =
    input.fold(listOf(50)) { acc, rotation ->
        acc + ((acc.last() + direction(rotation) * amount(rotation)).mod(100))
    }.count { it == 0 }

fun solvePart2(input: List<String>): Int =
    input.fold(Pair(50, 0)) { acc, rotation ->
        val nonNormalized = acc.first + direction(rotation) * amount(rotation)
        val fullRotations = nonNormalized.div(100).absoluteValue
        val backwardsOver0 = nonNormalized <= 0 && acc.first != 0
        Pair(
            nonNormalized.mod(100),
            acc.second + fullRotations + if (backwardsOver0) 1 else 0
        )
    }.second


fun main() {
    solvePart1(
        readInputLines("_2025/day1_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2025/day1_part1")
    )
        .also { println("Part two result: $it") }
}