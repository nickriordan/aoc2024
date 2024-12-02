import java.io.InputStreamReader

private val lineSplitRegex = "[\\s,]+".toRegex()

fun loadFile(fileName: String): Sequence<String> =
    object {}.javaClass.getResourceAsStream(fileName).let { InputStreamReader(it!!).readLines().asSequence() }

fun loadFileSplitLine(fileName: String) = loadFile(fileName).map { line -> line.split(lineSplitRegex) }.asSequence()

fun loadFileLinesOfIntegers(fileName: String) = loadFileSplitLine(fileName).map { line -> line.map { it.toInt()} }
