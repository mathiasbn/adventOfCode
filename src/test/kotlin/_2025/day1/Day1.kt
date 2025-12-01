package _2025.day1

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day1 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """
L68
L30
R48
L5
R60
L55
L1
L99
R14
L82
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 3
    }

    test("PartTwoExample") {
        solvePart2(
            """
L68
L30
R48
L5
R60
L55
L1
L99
R14
L82
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 6
    }

    test("Part two greater than 100") {
        solvePart2(
            """
R200
L200
R50
R1000
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 15
    }
})

