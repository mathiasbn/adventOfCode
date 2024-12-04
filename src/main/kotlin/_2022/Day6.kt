fun charsBeforePacketMarker(input: String): Int {
    return charsBeforeMarker(input, 4)
}
fun charsBeforeMessageMarker(input: String): Int {
    return charsBeforeMarker(input, 14)
}

private fun charsBeforeMarker(input: String, size: Int): Int {
    return input.windowed(size, 1).indexOfFirst{ it.toSet().count() == size } + size //Window look 14 ahead. We should count "parse over"
}

fun main() {
    println("Chars before packet marker detected: " + charsBeforePacketMarker(readResourceFile("day6_input.txt")))
    println("Chars before message marker detected: " + charsBeforeMessageMarker(readResourceFile("day6_input.txt")))

}