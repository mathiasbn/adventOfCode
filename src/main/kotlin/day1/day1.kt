package day1

import util.readInputLines

fun solvePart1(input: List<String>): Int =
    input.map { it.find { it.isDigit() }!! + it.findLast { it.isDigit() }!!.toString() }
        .map { it.toInt() }
        .sum()

fun solvePart2(input: List<String>): Int =
    input
        .map { findDigits(it).first() + findDigits(it).last() }
        .also { println(it) }
        .map { it.toInt() }
        .sum()

fun findDigits(search: String) =
    "(?<result>one|two|three|four|five|six|seven|eight|nine|[0-9])".toRegex()
        .let { regex -> search.indices.map { start -> regex.matchAt(search, start) } }
        .filterNotNull()
        .map { it.groups["result"]!!.value }
        .map {
            when (it) {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" -> it
                "one" -> "1"
                "two" -> "2"
                "three" -> "3"
                "four" -> "4"
                "five" -> "5"
                "six" -> "6"
                "seven" -> "7"
                "eight" -> "8"
                "nine" -> "9"
                else -> {
                    throw Error("unexpected digit: $it")
                }
            }
        }


fun main() {
    solvePart1(
        readInputLines("day1_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("day1_part1")
    )
        .also { println("Part two result: $it") }


}