fun main() {
    val data = loadFileLinesOfIntegers("day02-data.txt")

    fun isSafe(report: List<Int>) = report.windowed(2).let { pairs ->
        pairs.all { (a, b) -> b > a && b - a < 4 } || pairs.all { (a, b) -> a > b && a - b < 4 }
    }

    fun part1() = data.count { report -> isSafe(report) }

    fun part2() = data.count { rpt -> isSafe(rpt) || rpt.indices.any { i -> isSafe(rpt.take(i) + rpt.drop(i + 1)) } }

    println(part1())
    println(part2())
}
