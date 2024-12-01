import kotlinx.collections.immutable.toPersistentList
import java.io.InputStreamReader

fun loadFile(fileName: String): Sequence<String> =
    object {}.javaClass.getResourceAsStream(fileName).let { InputStreamReader(it!!).readLines().asSequence() }

fun loadFileSplitLine(fileName: String) = loadFile(fileName).map { splitLine(it) }.toPersistentList().asSequence()

private val lineSplitRegex = "[\\s,]+".toRegex()
fun splitLine(line: String) = line.split(lineSplitRegex).toPersistentList()
