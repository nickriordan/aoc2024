fun main() {
    fun part1(src: String) = "mul\\((\\d+),(\\d+)\\)".toRegex().findAll(src)
        .sumOf { result -> result.groupValues[1].toInt() * result.groupValues[2].toInt() }

    fun part2(src: String) = "(mul\\((\\d+),(\\d+)\\))|(don't)|(do)".toRegex().findAll(src).fold(true to 0) { acc, r ->
        when (r.value) {
            "do" -> true to acc.second
            "don't" -> false to acc.second
            else -> if (acc.first) (true to acc.second + (r.groupValues[2].toInt() * r.groupValues[3].toInt())) else acc
        }
    }.second

    val data = loadFile("day03-data.txt")
    println(part1(data))
    println(part2(data))
}