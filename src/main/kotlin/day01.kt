import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentMapOf
import kotlin.math.absoluteValue

fun main() {
    val (list1, list2) = loadFileLinesOfIntegers("day01-data.txt")
        .fold(listOf(emptyList(), emptyList<Int>())) { r, l -> listOf(r[0] + l[0], r[1] + l[1]) }

    fun part1() = list1.sorted().zip(list2.sorted()).sumOf { (a, b) -> (a - b).absoluteValue }

    fun part2() = list2.fold(persistentMapOf<Int, Int>()) { acc, v -> acc.mutate { it[v] = it.getOrDefault(v, 0) + 1 } }
        .let { secondListCounts -> list1.sumOf { it * secondListCounts.getOrDefault(it, 0) } }

    println(part1())
    println(part2())
}
