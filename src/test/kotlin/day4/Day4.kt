package day4

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimAndSplit

class Day4 : FunSpec({
    test("PartOneExample") {
        solvePart1(
            """
            """.trimAndSplit()
        ) shouldBe 4361
    }

    test("PartTwoExample") {
        solvePart2(
            """
            """.trimAndSplit()
        ) shouldBe 467835
    }
})
