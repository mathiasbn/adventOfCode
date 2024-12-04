fun parseInstructions(input: String) =
    input.lines().filter { it.isNotBlank() }.flatMap {
        if (it == "noop") listOf(0)
        else listOf(it.split(" ")[1].toInt(), 0)
    }

fun readInstructions(instructions: List<Int>): Sequence<Int> = sequence {
    yield(1)
    instructions.runningFold(1) { acc, i -> acc + i }.forEach { yield(it) }
}

fun Sequence<Int>.xDuringCycle(i: Int): Int = drop(i - 1).first()
fun Sequence<Int>.singnalStrengthDuringCycle(i: Int): Int = xDuringCycle(i)*i

fun drawInstructions(instructions: List<Int>) {
    readInstructions(instructions)
        .filterIndexed { index, _ -> index < 240 }
        .forEachIndexed { index: Int, x: Int ->
            val pixel = index % 40
            if (pixel == 0) {
                println()
            }
            val spritePos = listOf(x - 1, x, x + 1)
            if (spritePos.contains(pixel))
                print("#")
            else
                print(".")
        }
}

fun main() {
    val instructions = parseInstructions(readResourceFile("day10_input.txt"))
    val cycle20 = readInstructions(instructions).singnalStrengthDuringCycle(20)
    println(
        "Sum of signal strengths at cycle 20 ($cycle20), 60, 100, 140, 180, 220: " +
                (cycle20 +
                        readInstructions(instructions).singnalStrengthDuringCycle(60) +
                        readInstructions(instructions).singnalStrengthDuringCycle(100) +
                        readInstructions(instructions).singnalStrengthDuringCycle(140) +
                        readInstructions(instructions).singnalStrengthDuringCycle(180) +
                        readInstructions(instructions).singnalStrengthDuringCycle(220))
    )
    drawInstructions(instructions)
}
