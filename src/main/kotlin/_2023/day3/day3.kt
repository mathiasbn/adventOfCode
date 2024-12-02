package _2023.day3

import util.readInputLines

fun solvePart1(input: List<String>): Any {
    val potentialParts = input.flatMapIndexed { y, line -> findPotentialParts(y, line) }
    val partDeterminedPoints = partDeterminedPoints(input)
    return potentialParts.filter { partDeterminedPoints.intersect(adjacentPoints(it)).isNotEmpty() }
        .also { println(it) }
        .sumOf { it.id }
}

fun solvePart2(input: List<String>): Any {
    val potentialParts = input.flatMapIndexed { y, line -> findPotentialParts(y, line) }.toSet()
    val potentialGearPoints = potentialGearPoints(input)
    return findGears(potentialGearPoints, potentialParts)
        .sum()
}

fun adjacentPoints(potentialPart: PotentialPart): Set<Point> {
    val partXRange = 0..<potentialPart.id.toString().length
    val partPoints = partXRange.map { potentialPart.start.copy(x = potentialPart.start.x + it) }
    return partPoints
        .flatMap { adjacentPointsTo(it) }
        .filter { it !in partPoints }
        .toSet()
}

fun adjacentPointsTo(point: Point) =
    listOfNotNull(
        point.left(),
        point.upLeft(),
        point.up(),
        point.upRight(),
        point.right(),
        point.downRight(),
        point.down(),
        point.downLeft()
    )

fun partDeterminedPoints(plan: List<String>) = findPoints(plan, "[^\\d.]".toRegex())
fun potentialGearPoints(plan: List<String>) = findPoints(plan, "\\*".toRegex())

fun findGears(potentialGearsPoints: Set<Point>, partNumbers: Set<PotentialPart>): Set<Int> {
    val potentialGears = potentialGearsPoints.associateWith { mutableSetOf<PotentialPart>() }
    partNumbers.forEach { potentialPart ->
        adjacentPoints(potentialPart).forEach {
            potentialGears.get(it)?.add(potentialPart)
        }
    }
    return potentialGears.filterValues { it.size == 2 }
        .map { it.value.fold(1) { acc, potentialPart -> acc * potentialPart.id } }
        .toSet()
}

fun findPoints(
    plan: List<String>,
    search: Regex
): Set<Point> {
    return plan.flatMapIndexed { y, line ->
        search.findAll(line).map { Point(it.range.first, y) }
    }.toSet()
}

fun findPotentialParts(lineNr: Int, line: String) =
    "(?<part>\\d+)".toRegex().findAll(line)
        .map { PotentialPart(it.groups["part"]!!.value.toInt(), Point(it.range.first, lineNr)) }
        .toList()

data class PotentialPart(val id: Int, val start: Point)

data class Point(val x: Int, val y: Int) {
    fun left() = if (x > 0) copy(x = x - 1) else null
    fun upLeft() = if (x > 0 && y > 0) copy(x = x - 1, y = y - 1) else null
    fun up() = if (y > 0) copy(y = y - 1) else null
    fun upRight() = if (y > 0) copy(x = x + 1, y = y - 1) else null
    fun right() = copy(x = x + 1)
    fun downRight() = copy(x = x + 1, y = y + 1)
    fun down() = copy(y = y + 1)
    fun downLeft() = if (x > 0) copy(x = x - 1, y = y + 1) else null
}

fun main() {
    println("Day3\n")
    solvePart1(
        readInputLines("day3_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("day3_part1")
    )
        .also { println("Part two result: $it") }
}
