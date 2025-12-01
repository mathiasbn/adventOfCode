package _2024.day9

import util.Grid
import util.Point
import util.cartesianProduct
import util.readInputLines
import java.util.*


fun solvePart1(input: List<String>) = DiscMap.parse(input[0])
    .also { it.print() }
    .defragment()
    .also { println(it) }
    .filter { it != '.' }
    .mapIndexed{ index, c -> index.toLong() * c.toString().toLong() }
    .sum()

fun solvePart2(input: List<String>) = 0

data class DiscMap(val fileBlocks: SortedMap<Int, Int>, val spaceAfter: Map<Int, Int>) {
    companion object {
        fun parse(compressedFormat: String) =
            compressedFormat.map { it.toString().toInt() }
                .windowed(2, 2, partialWindows = true)
                .foldIndexed(mutableMapOf<Int, Int>() to mutableMapOf<Int, Int>()) { fileId: Int, acc, fileAndSpace: List<Int> ->
                    acc.first[fileId] = fileAndSpace[0]
                    acc.second[fileId] = fileAndSpace.getOrNull(1) ?: 0
                    acc
                }
                .let { (fileBlocks, freeSpaceBlocks) -> DiscMap(fileBlocks.toSortedMap(), freeSpaceBlocks) }
    }

    fun print() = println(presentation())

    fun presentation() = fileBlocks.keys.flatMap { fileId: Int ->
        ((0 until fileBlocks[fileId]!!).map { fileId.toString() } +
                (0 until spaceAfter[fileId]!!).map { "." })
    }
        .joinToString("")

    fun defragment(): String {
        val fileBlocksExploded = fileBlockExploded().joinToString("")
        return fileBlocks.keys.fold(fileBlocksExploded to "") { acc, index: Int ->
            var (exploded, result) = acc
            val fileBlockToTake = fileBlocks[index]!!
            val spaceToFill = spaceAfter.getOrDefault(index, 0)
            result += exploded.take(fileBlockToTake)
            exploded = exploded.drop(fileBlockToTake)
            result += exploded.takeLast(spaceToFill).reversed()
            exploded = exploded.dropLast(spaceToFill)
            exploded to result

        }.second + freeSpaceExploded().joinToString("")
    }

    private fun fileBlockExploded() = fileBlocks.values.flatMapIndexed { id, blocks ->
        (0 until blocks).map { id.toString() }
    }

    private fun freeSpaceExploded() = spaceAfter.values.flatMapIndexed { id, blocks ->
        (0 until blocks).map { "." }
    }

}


fun main() {
    solvePart1(
        readInputLines("_2024/day9_part1")
    )
        .also { println("Part one result: $it")
        println("20329637646 Too low")
        }

    solvePart2(
        readInputLines("_2024/day9_part1")
    )
        .also { println("Part two result: $it") }
}
