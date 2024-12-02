fun main() {
    val data = loadFileSplitLine("day02-data.txt").map { it.map { v -> v.toInt() } }

    fun isSafeReport(report: List<Int>) = report.windowed(2).let { pairs ->
        pairs.all { (a, b) -> b > a && b - a < 4 } || pairs.all { (a, b) -> a > b && a - b < 4 }
    }

    fun part1() = data.count { report -> isSafeReport(report) }

    fun part2() = data.count { report ->
        isSafeReport(report) || report.indices.any { n -> isSafeReport(report.take(n) + report.drop(n + 1)) }
    }

    println(part1())
    println(part2())
}
