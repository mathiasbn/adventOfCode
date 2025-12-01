package _2024.day9

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.*
import java.util.SortedMap

class Day9 : FunSpec({

    test("Parse disc map") {
        val (fileBlocks, freeSpaceBlocks) = DiscMap.parse("12345")
        fileBlocks shouldBe mapOf(0 to 1, 1 to 3, 2 to 5)
        freeSpaceBlocks shouldBe mapOf(0 to 2, 1 to 4, 2 to 0)
    }

    test("Draw DiscMap") {
        DiscMap.parse("12345").presentation() shouldBe "0..111....22222"
    }

    test("Defragment") {
        DiscMap.parse("12345").defragment() shouldBe "022111222......"
    }
    test("partOneExample") {
        solvePart1("2333133121414131402".trimIndent().trimSplit()) shouldBe 1928
    }

    test("PartTwoExample") {
        solvePart2(
            """""".trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe -1
    }
})

