package day5

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimAndSplit

class Day5 : FunSpec({

    test("PartOneExample") {
        solvePart1(
            """
            """.trimAndSplit()
        ) shouldBe 13
    }

    test("PartTwoExample") {
        solvePart2(
            """
            """.trimAndSplit()
        ) shouldBe 30
    }
})