package _2025.day2

import util.readInputLines
import kotlin.text.split

fun solvePart1(input: String): Long =
    input.split(",").map { range -> range.split("-").let { fromTo -> fromTo[0].toLong().rangeTo(fromTo[1].toLong()) } }
        .flatMap { it.toList() }.filter(::isInvalidId).sum()

fun isInvalidId(id: Long): Boolean {
    val idText = id.toString()
    return idText.length.mod(2) == 0 && idText.take(idText.length / 2) == idText.drop(idText.length / 2)
}

fun solvePart2(input: String): Long =
    input.split(",")
        .map { range ->
            range.split("-")
                .let { fromTo -> fromTo[0].toLong().rangeTo(fromTo[1].toLong()) }
        }
        .flatMap { it.toList() }
        .filter(::isInvalidRepetitiveId)
        .sum()


fun isInvalidRepetitiveId(i: Long): Boolean {
    val idText = i.toString()
    return (1..idText.length / 2).filter { idText.length.mod(it) == 0 }.any { windowLength ->
        val windows = idText.windowed(windowLength, windowLength)
        val pattern = windows[0]
        windows.all { it == pattern }
            .also { if (it) println("$pattern was repeated in $i with window length: $windowLength and windows $windows") }
    }.also { if (it) println("$i was invalid") }
}

fun main() {
    solvePart1(
        readInputLines("_2025/day2_part1").first()
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2025/day2_part1").first()
    )
        .also { println("Part two result: $it") }
}