package _2024.day2

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day2 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 2
    }

    test("PartTwoExample") {
        solvePart2(
            """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 4
    }

    test("permutations") {
        permutations(listOf(1,2,3)) shouldBe listOf(listOf(2,3), listOf(1,3), listOf(1,2))
    }
})

