fun main() {
    val directions = listOf('<', '>', '^', 'v')

    data class GuardPos(val location: Grid.Point, val direction: Char)

    class LabGrid(private val data: List<String>) : Grid(data[0].length, data.size) {

        val guardInitial = allPoints().first { data[it.y][it.x] in directions }.let { GuardPos(it, data[it.y][it.x]) }

        fun getPath(at: (Point) -> Char) = generateSequence({ guardInitial }) { guard ->
            fun move(direction: Char): GuardPos? = when (direction) {
                '^' -> guard.location.north()?.let { if (at(it) == '#') move('>') else GuardPos(it, direction) }
                'v' -> guard.location.south()?.let { if (at(it) == '#') move('<') else GuardPos(it, direction) }
                '>' -> guard.location.east()?.let { if (at(it) == '#') move('v') else GuardPos(it, direction) }
                '<' -> guard.location.west()?.let { if (at(it) == '#') move('^') else GuardPos(it, direction) }
                else -> throw Exception()
            }
            move(guard.direction)
        }

        fun originalPath() = getPath { data[it.y][it.x] }.map { it.location }.toSet()

        fun isPathLoop(path: Sequence<GuardPos>) = mutableSetOf<GuardPos>().let { seen -> path.any { !seen.add(it) } }

        fun part1() = originalPath().size

        fun part2() = originalPath().count { loc -> isPathLoop(getPath { if (it == loc) '#' else data[it.y][it.x] }) }
    }

    val grid = LabGrid(loadFileAsLines("day06-data.txt").toList())
    println(grid.part1())
    println(grid.part2())
}