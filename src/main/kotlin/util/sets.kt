package util

fun <T> cartesianProduct(sets: List<List<T>>) =
    sets.fold(listOf(listOf<T>())) { acc, set ->
        acc.flatMap { list -> set.map { element -> list + element } }
    }.toSet()
