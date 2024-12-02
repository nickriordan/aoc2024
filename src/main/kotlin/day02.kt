fun main() {
    val data = loadFileLinesOfIntegers("day02-data.txt")

    fun isSafe(rpt: List<Int>) =
        rpt.windowed(2).let { it.all { (a, b) -> b - a in 1..3 } || it.all { (a, b) -> a - b in 1..3 } }

    fun part1() = data.count { isSafe(it) }

    fun part2() = data.count { rpt -> isSafe(rpt) || rpt.indices.any { i -> isSafe(rpt.take(i) + rpt.drop(i + 1)) } }

    println(part1())
    println(part2())
}
