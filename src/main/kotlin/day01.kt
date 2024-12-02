import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.absoluteValue

fun main() {
    val data = loadFileSplitLine("day01-data.txt")
    val firstList = data.map { it[0].toInt() }.toPersistentList()
    val secondList = data.map { it[1].toInt() }.toPersistentList()

    fun part1() = firstList.sorted().zip(secondList.sorted()).sumOf { (a, b) -> (a - b).absoluteValue }

    fun part2() = secondList.fold(persistentMapOf<Int, Int>()) { acc, v ->
        acc.mutate { it[v] = it.getOrDefault(v, 0) + 1 }
    }.let { secondListCounts ->
        firstList.sumOf { it * secondListCounts.getOrDefault(it, 0) }
    }

    println(part1())
    println(part2())
}
