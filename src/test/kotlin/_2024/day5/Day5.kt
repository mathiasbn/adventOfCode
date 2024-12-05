package _2024.day5

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplit
import util.trimSplitRemoveEmptyLines

class Day5 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """
                47|53
                97|13
                97|61
                97|47
                75|29
                61|13
                75|53
                29|13
                97|29
                53|29
                61|53
                97|53
                61|29
                47|13
                75|47
                97|75
                47|61
                75|61
                47|29
                75|13
                53|13

                75,47,61,53,29
                97,61,53,29,13
                75,29,13
                75,97,47,61,53
                61,13,29
                97,13,75,29,47
            """.trimSplit()
        ) shouldBe 143
    }

    test("Parse rules") {
        val rules = parseRules(listOf("1|2", "33|44", "", "1,33,44"))
        rules[1 to 2] shouldBe -1
        rules[2 to 1] shouldBe 1
        rules[33 to 44] shouldBe -1
        rules[44 to 33] shouldBe 1
    }

    test("Parse updates") {
        val updates = parseUpdates(listOf("1|2", "33|44", "", "1,33,44", "55,66"))
        updates[0] shouldBe listOf(1, 33, 44)
        updates[1] shouldBe listOf(55, 66)
    }

    test("Middle page number") {
        middlePageNumber(listOf(1,2,3)) shouldBe 2
        middlePageNumber(listOf(1,2,3,4,5)) shouldBe 3
    }

    test("Order checker") {
        isCorrectOrder(listOf(1, 2, 3), parseRules(listOf("1|2", "2|3"))) shouldBe true
        isCorrectOrder(listOf(1, 2, 3), parseRules(listOf("1|2", "3|2"))) shouldBe false
        //This seems partial :S
        isCorrectOrder(listOf(1, 2, 4, 3, 5, 6), parseRules(listOf("1|2", "2|3", "2|4", "5|6", "3|5"))) shouldBe true
    }

    test("PartTwoExample") {
        solvePart2(
            """""".trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe -1
    }
})
