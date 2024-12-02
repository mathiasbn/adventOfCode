package _2023.day5

import util.readInputLines

fun solvePart1(input: List<String>): Long {
    return parse(input) { map { it..it } }.seedLocations().values.flatten().minOf { it.first }
}

fun solvePart2(input: List<String>): Long {
    return parse(input) {
        this.windowed(2, 2).map { (from, length) -> from..<from + length }
    }.seedLocations().values.flatten().minOf { it.first }
}


fun parse(input: List<String>, seedRanges: List<Long>.() -> List<LongRange>): Almanac {
    val seeds = input.first().removePrefix("seeds: ").split(" ")
        .map(String::trim).map(String::toLong).seedRanges()
    return Almanac(
        seeds,
        parseCategoryConversion(input, "seed-to-soil"),
        parseCategoryConversion(input, "soil-to-fertilizer"),
        parseCategoryConversion(input, "fertilizer-to-water"),
        parseCategoryConversion(input, "water-to-light"),
        parseCategoryConversion(input, "light-to-temperature"),
        parseCategoryConversion(input, "temperature-to-humidity"),
        parseCategoryConversion(input, "humidity-to-location"),
    )
}

private fun parseCategoryConversion(input: List<String>, conversionTitle: String): CategoryConversion =
    CategoryConversion(input.dropWhile { !it.startsWith(conversionTitle) }.drop(1).takeWhile { it.isNotBlank() }
        .map {
            val (a, b, c) = it.split(" ").map(String::toLong)
            DestSourceRange(a, b, c)
        })

data class Almanac(
    val seeds: List<LongRange>,
    val seedToSoil: CategoryConversion,
    val soilToFertilizer: CategoryConversion,
    val fertilizerToWater: CategoryConversion,
    val waterToLight: CategoryConversion,
    val lightToTemperature: CategoryConversion,
    val temperatureToHumidity: CategoryConversion,
    val humidityToLocation: CategoryConversion,
) {

    fun seedToLocation(seed: LongRange): List<LongRange> {
        val soil = seedToSoil[listOf(seed)]
        val fertilizer = soilToFertilizer[soil]
        val water = fertilizerToWater[fertilizer]
        val light = waterToLight[water]
        val temperature = lightToTemperature[light]
        val humidity = temperatureToHumidity[temperature]
        return humidityToLocation[humidity]
    }

    fun seedLocations() = seeds.associateWith(::seedToLocation)

}

data class DestSourceRange(val dest: Long, val source: Long, val range: Long) {
    val lastSource = source + range - 1

    operator fun contains(source: Long): Boolean = source in this.source..<this.source + range
    operator fun get(i: Long): Long {
        if (i in this) return dest + (i - source)
        throw Exception("Out of range: $i !in $this")
    }

    operator fun get(i: LongRange): LongRange {
        if (i.first in this && i.last in this) return this[i.first]..this[i.last]
        throw Exception("Out of range: $i !in $this")
    }
}

class CategoryConversion(val ranges: List<DestSourceRange>) {
    operator fun get(single: Long) = get(listOf(single..single)).first().first
    operator fun get(sourceRanges: List<LongRange>): List<LongRange> {
        return sourceRanges.flatMap { sourceRange ->
            ranges.fold(
                Pair(
                    mutableListOf<LongRange>(),
                    mutableListOf(sourceRange)
                )
            ) { (matched, notMatched), range ->
                val newNotMatched = notMatched.mapNotNull { noMatch ->
                    if (noMatch.first in range && noMatch.last in range) {
                        matched.add(range[noMatch])
                        null
                    } else if (noMatch.first in range) {
                        matched.add(range[noMatch.first..range.lastSource])
                        range.lastSource + 1..noMatch.last
                    } else if (noMatch.last in range) {
                        matched.add(range[range.source..noMatch.last])
                        noMatch.first..range.source
                    } else
                        noMatch
                }.toMutableList()
                Pair(matched, newNotMatched)
            }.let { it.first + it.second }
        }
    }

}

fun main() {
    println("Day5\n")
    solvePart1(
        readInputLines("day5_part1", allowEmptyLines = true)
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("day5_part1", allowEmptyLines = true)
    )
        .also { println("Part two result: $it") }
}
