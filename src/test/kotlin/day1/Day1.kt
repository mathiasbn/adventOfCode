package day1

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day1 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 142
    }

    test("resolveSpelledOutDigits") {
        findDigits("1").first() shouldBe "1"
        findDigits("aaa1").first() shouldBe "1"
        findDigits("one2").first() shouldBe "1"
        findDigits("two1").first() shouldBe "2"
        findDigits("three1").first() shouldBe "3"
        findDigits("four1").first() shouldBe "4"
        findDigits("five1").first() shouldBe "5"
        findDigits("six1").first() shouldBe "6"
        findDigits("seven1").first() shouldBe "7"
        findDigits("eight1").first() shouldBe "8"
        findDigits("nine1").first() shouldBe "9"

        findDigits("3eightwoasdasd").last() shouldBe "2"
    }

    test("PartTwoExample") {
        solvePart2(
            """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 281
    }
})
