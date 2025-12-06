package _2025.day5

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplit
import util.trimSplitRemoveEmptyLines

class Day5 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """
                3-5
                10-14
                16-20
                12-18

                1
                5
                8
                11
                17
                32
            """.trimIndent().trimSplit()
        ) shouldBe 3
    }

    test("PartTwoExample") {
        solvePart2(
            """
                3-5
                10-14
                16-20
                12-18

                1
                5
                8
                11
                17
                32                
            """.trimIndent().trimSplit()
        ) shouldBe 14
    }
})

