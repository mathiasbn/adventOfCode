package _2024.day4

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day4 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """
                MMMSXXMASM
                MSAMXMSMSA
                AMXSXMAAMM
                MSAMASMSMX
                XMASAMXAMM
                XXAMMXXAMA
                SMSMSASXSS
                SAXAMASAAA
                MAMMMXMMMM
                MXMXAXMASX
            """.trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 18
    }

    test("PartTwoExample") {
        solvePart2(
            """
                MMMSXXMASM
                MSAMXMSMSA
                AMXSXMAAMM
                MSAMASMSMX
                XMASAMXAMM
                XXAMMXXAMA
                SMSMSASXSS
                SAXAMASAAA
                MAMMMXMMMM
                MXMXAXMASX
            """.trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 9
    }
})