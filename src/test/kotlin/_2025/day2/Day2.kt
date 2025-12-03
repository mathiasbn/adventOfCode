package _2025.day2

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day2 : FunSpec({

    test("invalid ids") {
        isInvalidId(22) shouldBe true
        isInvalidId(6464) shouldBe true
        isInvalidId(123123L) shouldBe true

        isInvalidId(23L) shouldBe false
        isInvalidId(123643L) shouldBe false
        isInvalidId(123321L) shouldBe false
    }

    test("invalid repetitive ids") {
        isInvalidRepetitiveId(22) shouldBe true
        isInvalidRepetitiveId(6464) shouldBe true
        isInvalidRepetitiveId(123123) shouldBe true
        isInvalidRepetitiveId(11111) shouldBe true


        isInvalidRepetitiveId(23) shouldBe false
        isInvalidRepetitiveId(123643) shouldBe false
        isInvalidRepetitiveId(123321) shouldBe false
        isInvalidRepetitiveId(2121212118) shouldBe false
    }

    test("partOneExample") {
        solvePart1(
            "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
        ) shouldBe 1227775554
    }

    test("PartTwoExample") {
        solvePart2("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
        ) shouldBe 4174379265
    }
})
