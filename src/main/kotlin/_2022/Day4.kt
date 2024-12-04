fun fullSubSection(input: String): Int =
    pairWorkAssignments(input)
        .filter {
            it.first.toSet().containsAll(it.second.toSet()) ||
                    it.second.toSet().containsAll(it.first.toSet())
        }
        .count()

fun overlappingPairAssignments(input: String): Int =
    pairWorkAssignments(input)
        .filter { it.first.intersect(it.second).isNotEmpty() }
        .count()

private fun pairWorkAssignments(input: String) = input.lines().filter { it.isNotBlank() }
    .map { it.split(",").let { Pair(it[0], it[1]) } }
    .map { Pair(toRange(it.first), toRange(it.second)) }


private fun toRange(range: String) = range.split("-").let { it[0].toInt()..it[1].toInt() }

fun main() {
    println("The amount of elfs whos work is completely overlapped: " + fullSubSection(readResourceFile("day4_input.txt")))
    println("The amount of elfs pairs with overlapping assignments: " + overlappingPairAssignments(readResourceFile("day4_input.txt")))
}