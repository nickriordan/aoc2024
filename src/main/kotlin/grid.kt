abstract class Grid(val xSize: Int, val ySize: Int) {

    inner class Point(val x: Int, val y: Int) {
        fun north() = if (y > 0) Point(x, y - 1) else null
        fun northSequence() = generateSequence({ this }) { it.north() }

        private fun northEast() = if (y > 0 && x < xSize - 1) Point(x + 1, y - 1) else null
        fun northEastSequence() = generateSequence({ this }) { it.northEast() }

        fun east() = if (x < xSize - 1) Point(x + 1, y) else null
        fun eastSequence() = generateSequence({ this }) { it.east() }

        private fun southEast() = if (x < xSize - 1 && y < ySize - 1) Point(x + 1, y + 1) else null
        fun southEastSequence() = generateSequence({ this }) { it.southEast() }

        fun south() = if (y < ySize - 1) Point(x, y + 1) else null
        fun southSequence() = generateSequence({ this }) { it.south() }

        fun southWest() = if (x > 0 && y < ySize - 1) Point(x - 1, y + 1) else null
        fun southWestSequence() = generateSequence({ this }) { it.southWest() }

        fun west() = if (x > 0) Point(x - 1, y) else null
        fun westSequence() = generateSequence({ this }) { it.west() }

        fun northWest() = if (x > 0 && y > 0) Point(x - 1, y - 1) else null
        fun northWestSequence() = generateSequence({ this }) { it.northWest() }

        internal fun nextSouthLine() = if (y < ySize - 1) Point(0, y + 1) else null

        fun isValid() = x >= 0 && y >= 0 && x < xSize && y < ySize
        fun validOrNull() = if (isValid()) this else null

        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y)

        override fun equals(other: Any?) = (other === this) || (other is Point && other.x == x && other.y == y)

        override fun hashCode(): Int = x * 499 + y

        override fun toString() = "($x, $y)"
    }

    fun allPoints(): Sequence<Point> = generateSequence({ Point(0, 0) }) { it.east() ?: it.nextSouthLine() }

    fun List<Point>.allPermutations() = indices.flatMap { ix1 ->
        (ix1 + 1..lastIndex).map { ix2 -> this[ix1] to this[ix2] }
    }

}
