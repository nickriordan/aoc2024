import kotlin.math.absoluteValue

fun main() {
    val (left, right) = loadFileLinesOfIntegers("day01-data.txt")
        .fold(listOf(emptyList(), emptyList<Int>())) { r, l -> listOf(r[0] + l[0], r[1] + l[1]) }

    fun part1() = left.sorted().zip(right.sorted()).sumOf { (a, b) -> (a - b).absoluteValue }

    fun part2() = right.groupingBy { it }.eachCount().let { rightCount -> left.sumOf { it * (rightCount[it] ?: 0) } }

    println(part1())
    println(part2())
}
