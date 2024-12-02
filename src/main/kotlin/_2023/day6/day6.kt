package _2023.day6

import util.parseIntList
import util.readInputLines
import util.size

fun solvePart1(input: List<String>): Long {
    return parse(input) { parseIntList().map { it.toLong() } }.races.map { race -> race.winningRange().size() }
        .also { println(it) }
        .reduce(Long::times)
}

fun solvePart2(input: List<String>): Long {
    return parse(input) { listOf(replace(" ", "").toLong()) }.races.map { race -> race.winningRange().size() }
        .also { println(it) }
        .reduce(Long::times)
}


fun parse(input: List<String>, parser: String.() -> List<Long>): BoatRaces {
    return input.first().removePrefix("Time:").parser()
        .zip(input[1].removePrefix("Distance:").parser())
        .map { Race(it.first, it.second) }
        .let { BoatRaces(it) }
}

data class BoatRaces(val races: List<Race>)
data class Race(val ms: Long, val bestDistance: Long) {
    fun winningRange(): LongRange {
        return (1..<ms).filter { distance(it) > bestDistance }.let { it.first()..it.last() }
    }

    private fun distance(chargeMs: Long) = (ms - chargeMs) * chargeMs
}

fun main() {
    println("Day6\n")
    solvePart1(
        readInputLines("day6_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("day6_part1")
    )
        .also { println("Part two result: $it") }
}
