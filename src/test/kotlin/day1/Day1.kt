package day1

import kotlin.test.Test
import kotlin.test.assertEquals

class Day1 {
    @Test
    fun testPartOneExampel() {
        assertEquals(142, solvePart1(
            """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent().split("\n")
        ))
    }

    @Test
    fun resolveSpelledOutDigits(){
        assertEquals("1", findDigit("1"){first()})
        assertEquals("1", findDigit("aaa1"){first()})
        assertEquals("1", findDigit("one2"){first()})
        assertEquals("2", findDigit("two1"){first()})
        assertEquals("3", findDigit("three1"){first()})
        assertEquals("4", findDigit("four1"){first()})
        assertEquals("5", findDigit("five1"){first()})
        assertEquals("6", findDigit("six1"){first()})
        assertEquals("7", findDigit("seven1"){first()})
        assertEquals("8", findDigit("eight1"){first()})
        assertEquals("9", findDigit("nine1"){first()})

        assertEquals("2", findDigit("3eightwoasdasd"){last()})
    }
    @Test
    fun testPartTwoExampel() {
        assertEquals(281, solvePart2(
            """
two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
    """.trimIndent().split("\n").filter { it.isNotEmpty() }
        ))
    }
}
