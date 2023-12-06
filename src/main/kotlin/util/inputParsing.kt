package util

import kotlin.streams.toList

fun readInputLines(inputName: String, allowEmptyLines:Boolean=false): List<String> =
    object {}.javaClass.getResourceAsStream("/$inputName.txt")?.bufferedReader()!!.lines().filter { allowEmptyLines||it.isNotEmpty() }
        .toList()

fun String.trimSplitRemoveEmptyLines() = this.trimIndent().split("\n").filter { it.isNotEmpty() }
fun String.trimSplit() = this.trimIndent().split("\n")
fun String.parseIntList() = this.split(" ").filter(String::isNotBlank).map(String::toInt)
fun String.parseLongList() = this.split(" ").filter(String::isNotBlank).map(String::toLong)
