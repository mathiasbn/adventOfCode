package day2

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimAndSplit

class Day2 : FunSpec({
    test("PartOneExample") {
        solvePart1(
            """
            """.trimAndSplit()
        ) shouldBe 42
    }

    test("PartTwoExample") {
        solvePart2(
            """
            """.trimAndSplit()
        ) shouldBe 42
    }
})
