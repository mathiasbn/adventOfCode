package day8

import day8.Selection.*
import util.readInputLines

fun solvePart1(input: List<String>) {
    parse(input).followFrom("AAA")
}

fun solvePart2(input: List<String>) {
}


enum class Selection { Left, Right }

fun parse(input: List<String>): MapOfInstructions {
    val selections = input.first().map {
        when (it) {
            'L' -> Left
            'R' -> Right
            else -> throw Exception("Unknown selectio: $it")
        }
    }
    val directions = input.drop(2).associateBy(
        { it.split("=").first().trim() },
        {
            val (left, right) = it.split("=")[1]
                .trim().removePrefix("(").removeSuffix(")")
                .split(",")
            Direction(left.trim(), right.trim())
        }
    )

    return MapOfInstructions(selections, directions)
}

data class MapOfInstructions(val selections: List<Selection>, val directions: Map<String, Direction>) {
    fun followFrom(start: String): String {
        return selections.foldIndexed(start){i, acc: String, selection: Selection ->
            val direction = directions[acc]!!
            when(selection){
                Left -> direction.left
                Right -> direction.right
            }
        }
    }

}

data class Direction(val left: String, val right: String)


fun main() {
    println("Day8\n")
    solvePart1(
        readInputLines("day8_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("day8_part1")
    )
        .also { println("Part two result: $it") }
}
