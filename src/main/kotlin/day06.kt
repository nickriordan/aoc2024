fun main() {
    data class GuardPos(val location: Grid.Point, val direction: Char)
    class LabGrid(private val data: List<String>) : Grid(data[0].length, data.size) {
        private fun cellAt(pt: Point) = data[pt.y][pt.x]
        private val initial = allPoints().first { cellAt(it) in "<>^v" }.let { GuardPos(it, cellAt(it)) }

        private fun pathSequence(at: (Point) -> Char) = generateSequence({ initial }, { guard ->
            fun move(direction: Char): GuardPos? = when (direction) {
                '^' -> guard.location.north()?.let { if (at(it) == '#') move('>') else GuardPos(it, direction) }
                'v' -> guard.location.south()?.let { if (at(it) == '#') move('<') else GuardPos(it, direction) }
                '>' -> guard.location.east()?.let { if (at(it) == '#') move('v') else GuardPos(it, direction) }
                '<' -> guard.location.west()?.let { if (at(it) == '#') move('^') else GuardPos(it, direction) }
                else -> throw Exception()
            }
            move(guard.direction)
        })

        private fun isLoop(p: Sequence<GuardPos>) = mutableSetOf<GuardPos>().let { seen -> p.any { !seen.add(it) } }

        private val path = pathSequence(::cellAt).map { it.location }.toSet()
        fun part1() = path.size
        fun part2() = path.count { loc -> isLoop(pathSequence { if (it == loc) '#' else cellAt(it) }) }
    }

    val grid = LabGrid(loadFileAsLines("day06-data.txt").toList())
    println(grid.part1())
    println(grid.part2())
}