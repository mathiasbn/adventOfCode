package util

import kotlin.streams.toList

fun readInputLines(inputName: String): List<String> =
    object {}.javaClass.getResourceAsStream("/$inputName.txt")?.bufferedReader()!!.lines().filter { it.isNotEmpty() }
        .toList()

fun String.trimAndSplit() = this.trimIndent().split("\n").filter { it.isNotEmpty() }