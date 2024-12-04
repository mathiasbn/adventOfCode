private fun itemValue(it: Int) = if (it in 65..90) it - 64 + 26 else it - 96

fun findMisplacedItemsValue(input: String): Int {
    return input.lines().filter { it.isNotBlank() }
        .map { it.chars().toArray().let { Pair(it.take(it.size / 2), it.drop(it.size / 2)) } }
        .map { it.first.intersect(it.second).single() }
        .map { itemValue(it) }
        .sum()
}


fun findBadgesItemValue(input: String): Int {
    return input.lines().filter { it.isNotBlank() }
        .windowed(3, 3)
        .map { it.map { it.chars().toArray().toSet() }.reduce { acc, ints -> acc.intersect(ints) }.single() }
        .map { itemValue(it) }
        .sum()
}

fun main() {
    println("Value of mispacked: " + findMisplacedItemsValue(readResourceFile("day3_input.txt")))
    println("Group badge values: " + findBadgesItemValue(readResourceFile("day3_input.txt")))
}