fun main() {

    class CityMap(private val data: List<String>) : Grid(data[0].length, data.size) {
        fun antennasLocationsByFreq() =
            allPoints().map {
                data[it.y][it.x] to it
            }.filter {
                it.first !in ".#"
            }.groupBy({ (ch, _) -> ch }, { (_, pt) -> pt })

        fun allPairOfPoints(points: List<Point>) =
            points.indices.flatMap { ix1 ->
                (ix1 + 1..points.lastIndex).map { ix2 -> points[ix1] to points[ix2] }
            }

        fun findAntiNodes(antiNodesOnPath: (Point, Point) -> List<Point>): Set<Point> =
            antennasLocationsByFreq().flatMap { (_, locations) ->
                allPairOfPoints(locations).flatMap { (p1, p2) -> antiNodesOnPath(p1, p2) }
            }.toSet()

        fun part1() = findAntiNodes { pt1, pt2 ->
            val offset = pt1 - pt2
            listOf(pt1 + offset, pt2 - offset).filter { it.isValid() }
        }.count()

        fun part2() = findAntiNodes { pt1, pt2 ->
            val offset = pt1 - pt2
            listOf(pt1, pt2) +
                    generateSequence({ (pt1 + offset).validOrNull() }) { (it + offset).validOrNull() }.toList() +
                    generateSequence({ (pt2 - offset).validOrNull() }) { (it - offset).validOrNull() }.toList()
        }.count()
    }

    val map = CityMap(loadFileAsLines("day08-data.txt").toList())

    println(map.part1())
    println(map.part2())
}