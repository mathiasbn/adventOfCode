package _2024.day6

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.*

class Day6 : FunSpec({

    test("Find start") {
        val (point, dir) = findStart(Grid(listOf(".#..^.....")))
        point shouldBe Point(4, 0)
        dir shouldBe Direction.U
    }

    test("Simple walk") {
        val seq = walkSequence(
            Grid(
                listOf(
                    "..........",
                    "..........",
                    "..........",
                    "..........",
                    ".#..^.....",
                )
            )
        )
        seq.last().point shouldBe Point(4, 0)
    }

    test("Walk with turn") {
        val seq = walkSequence(
            Grid(
                listOf(
                    "....#.....",
                    ".#..^.....",
                )
            )
        )
        seq.last().point shouldBe Point(9, 1)
    }

    test("partOneExample") {
        solvePart1(
            """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
            """.trimIndent().trimSplit()
        ) shouldBe 41
    }

    test("PartTwoExample") {
        solvePart2(
            """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
            """.trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 6
    }
})