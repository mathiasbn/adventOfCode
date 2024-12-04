data class Monkey(
    val monkey: Long,
    val items: MutableList<Long>,
    val worryFunc: (Long) -> Long,
    val worryTestDivider: Long,
    val worryTest: (Long) -> Int
) {
    var inspections: Long = 0.toLong()

}

data class Throw(val receiver: Int, val item: Long)

fun parseMonkeys(input: String): List<Monkey> {
    return input.lines().filter { it.isNotBlank() }.windowed(6, 6).map(::parseMonkey)
}

fun parseMonkey(input: String): Monkey = parseMonkey(input.lines())

fun parseMonkey(monkeyLines: List<String>): Monkey {
    val monkey = monkeyLines[0].split(" ")[1].replace(":", "").toLong()
    val items = monkeyLines[1].trim().substring("Starting items: ".length).split(", ").map { it.toLong() }
    val worryFunc = monkeyLines[2].trim().substring("Operation: new = old ".length).split(" ")
        .let {
            { old: Long ->
                when (it[0]) {
                    "+" -> old + (it[1].toLongOrNull() ?: old)
                    "*" -> old * (it[1].toLongOrNull() ?: old)
                    else -> throw Exception("Unknown operation $it")
                }
            }
        }
    val worryTestDivider = monkeyLines[3].trim().split(" ")[3].toLong()
    val worryTest = { worryLevel: Long ->
        if (worryLevel.mod(worryTestDivider) == 0.toLong())
            monkeyLines[4].trim().substring("If true: throw to monkey ".length).toInt()
        else
            monkeyLines[5].trim().substring("If false: throw to monkey ".length).toInt()
    }


    return Monkey(monkey, items.toMutableList(), worryFunc, worryTestDivider, worryTest)
}

fun Monkey.takeTurn(relaxing: Boolean = true, max: Long = 0): List<Throw> {
    return items.map {
        var newItemWorry = worryFunc(it)
        if (relaxing)
            newItemWorry /= 3
        else
            newItemWorry %= max
        val receiver = worryTest(newItemWorry)
        inspections++
        Throw(receiver, newItemWorry)
    }.also { items.clear() }
}

fun List<Monkey>.takRound(relaxing: Boolean = true) {
    forEach { monkey ->
        val throws = monkey.takeTurn(relaxing, map { it.worryTestDivider }.reduce { a, b -> a * b })
        throws.forEach {
            this[it.receiver].items.add(it.item)
        }
    }
}

fun List<Monkey>.monkeyBusiness(): Long =
    map { it.inspections }.sortedDescending().take(2).let { it[0] * it[1] }

fun main() {
//    println("The amount of monkey business: " + parseMonkeys(readResourceFile("day11_input.txt"))
//        .also { monkeys -> (1..20).forEach { monkeys.takRound() } }
//        .monkeyBusiness())

    println("The amount of monkey business: " +
            parseMonkeys(readResourceFile("day11_input.txt"))
                .also { monkeys -> (1..10000).forEach { monkeys.takRound(false) } }
                .also { println("Inspections: " + it.map { it.inspections }) }
                .also { println("Dividers: " + it.map { it.worryTestDivider }) }
                .monkeyBusiness())
}