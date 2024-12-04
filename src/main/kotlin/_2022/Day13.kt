import PacketValue.IntValue
import PacketValue.ListValue

sealed interface PacketValue: Comparable<PacketValue> {
    data class ListValue(val list: List<PacketValue>) : PacketValue, List<PacketValue> by list {
        override fun compareTo(other: PacketValue) = compare(this, other)
    }

    data class IntValue(val int: Int) : PacketValue {
        override fun compareTo(other: PacketValue) = compare(this, other)
    }
}

fun parsePackets(input: String): List<Pair<ListValue, ListValue>> {
    return input.lines().filter { it.isNotBlank() }.windowed(2, 2)
        .map { Pair(parsePacket(it[0]), parsePacket(it[1])) }
}

fun parsePacket(input: String): ListValue {
    return parseListValue(0, input).second
}

fun parseListValue(startOfList: Int, input: String): Pair<Int, ListValue> {
    val values = mutableListOf<PacketValue>()
    var i = startOfList + 1
    while (true) {
        when {
            input[i].isDigit() -> {
                val numberStr = input.substring(i).takeWhile { it.isDigit() }
                values += IntValue(numberStr.toInt())
                i += numberStr.length
            }

            input[i] == '[' -> {
                val (newI, innerListValue) = parseListValue(i, input)
                values.add(innerListValue)
                i = newI
            }

            input[i] == ',' -> {
                i++
            }

            input[i] == ']' -> {
                i++
                return Pair(i, ListValue(values))
            }

            else -> throw Exception("Have no idea what happened. We met char: '${input[i]}'")
        }
    }
}

fun Pair<PacketValue, PacketValue>.rightOrder(): Boolean {
    val (left, right) = this
    return left < right
}

fun compare(left: PacketValue, right: PacketValue): Int {
    return when {
        left is ListValue && right is ListValue -> compare(left, right)
        left is IntValue && right is IntValue -> compare(left, right)
        left is IntValue && right is ListValue -> compare(ListValue(listOf(left)), right)
        left is ListValue && right is IntValue -> compare(left, ListValue(listOf(right)))
        else -> throw Exception("unsupported combination of PacketValue")
    }
}

fun compare(left: IntValue, right: IntValue) = left.int.compareTo(right.int)

fun compare(left: ListValue, right: ListValue): Int {
    var i = 0
    while (true) {
        val lefti = left.getOrNull(i)
        val righti = right.getOrNull(i)
        when {
            lefti == null && righti == null -> return 0
            lefti == null && righti != null -> return -1
            lefti != null && righti == null -> return 1
            else -> {
                compare(lefti!!, righti!!).takeIf { it!=0  }?.let { return it }
            }
        }
        i++
    }
}

fun List<Pair<ListValue, ListValue>>.rightOrderPairs() =
    mapIndexed { index, pair -> if (pair.rightOrder()) index + 1 else null }
        .filterNotNull()

fun productOfDividerIndices(input: String): Int {
    val divider1 = ListValue(listOf(ListValue(listOf(IntValue(2)))))
    val divider2 = ListValue(listOf(ListValue(listOf(IntValue(6)))))
    val sortedPackets = parsePackets(input)
        .flatMap { listOf(it.first, it.second) }
        .plus(divider1 as PacketValue).plus(divider2 as PacketValue)
        .sorted()
    val divider1Index = sortedPackets.indexOfFirst { it == divider1 } + 1
    val divider2Index = sortedPackets.indexOfFirst { it == divider2 } + 1
    return divider1Index * divider2Index
}

fun main() {
    println("Sum of indices of correctly ordered pairs: "+parsePackets(readResourceFile("day13_input.txt"))
        .rightOrderPairs()
        .sum())
    println("Product of divider indices: "+productOfDividerIndices(readResourceFile("day13_input.txt")))
}
