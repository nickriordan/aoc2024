fun main() {
    fun part1(src: String) = "mul\\((\\d+),(\\d+)\\)".toRegex().findAll(src)
        .sumOf { r -> r.groupValues[1].toInt() * r.groupValues[2].toInt() }

    fun part2(src: String) = "(mul\\((\\d+),(\\d+)\\))|(don't)|(do)".toRegex().findAll(src)
        .fold(Pair(true, 0)) { acc, r ->
            when {
                r.value == "do" -> Pair(true, acc.second)
                r.value == "don't" -> Pair(false, acc.second)
                acc.first -> Pair(true, acc.second + (r.groupValues[2].toInt() * r.groupValues[3].toInt()))
                else -> acc
            }
        }.second

    loadFile("day03-data.txt").also { data ->
        println(part1(data))
        println(part2(data))
    }
}