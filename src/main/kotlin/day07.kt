fun main() {
    fun findTarget(target: Long, src: List<Long>, includeConcat: Boolean): Boolean {
        fun calc(total: Long, remain: List<Long>): Boolean =
            if (remain.isEmpty())
                (total == target)
            else {
                calc(total + remain.first(), remain.drop(1)) ||
                        calc(total * remain.first(), remain.drop(1)) ||
                        (includeConcat && calc((total.toString() + remain.first()).toLong(), remain.drop(1)))
            }

        return calc(0, src)
    }

    val data = loadFileAsLines("day07-test-data.txt")
        .map { it.split(": ") }
        .map { Pair(it[0].toLong(), it[1].split(" ").map { s -> s.toLong() }) }
        .toList()

    fun calculateSum(allowConcat: Boolean) =
        data.filter { equation -> findTarget(equation.first, equation.second, allowConcat) }.sumOf { it.first }

    println(calculateSum(false))
    println(calculateSum(true))
}