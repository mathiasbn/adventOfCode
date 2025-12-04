package _2025.day3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day3 : FunSpec({
    test("Highest joltage") {
        highestJoltage("987654321111111") shouldBe 98
        highestJoltage("811111111111119") shouldBe 89
        highestJoltage("234234234234278") shouldBe 78
        highestJoltage("818181911112111") shouldBe 92
    }
    test("Highest joltage with 12 batteries") {
        highestJoltage("987654321111111",12) shouldBe 987654321111
        highestJoltage("811111111111119",12) shouldBe 811111111119
        highestJoltage("234234234234278",12) shouldBe 434234234278
        highestJoltage("818181911112111",12) shouldBe 888911112111
    }
    test("partOneExample") {
        solvePart1(
            """
                987654321111111
                811111111111119
                234234234234278
                818181911112111
""".trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 357
    }

    test("PartTwoExample") {
        solvePart2(
            """
                987654321111111
                811111111111119
                234234234234278
                818181911112111
""".trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 3121910778619
    }
})

