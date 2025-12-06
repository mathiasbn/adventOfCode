package util

import kotlin.math.max
import kotlin.math.min

fun ClosedRange<Long>.size() = endInclusive - start + 1

fun ClosedRange<Long>.overlaps(other: ClosedRange<Long>) = contains(other.start) || contains(other.endInclusive)
fun ClosedRange<Long>.merge(other: ClosedRange<Long>) = min(start, other.start)..max(endInclusive, other.endInclusive)
