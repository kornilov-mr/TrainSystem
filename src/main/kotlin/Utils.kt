package org.example


import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.TreeSet

/**
 * Function which reads all lines containing train system description from inputSource
 * @param inputSource - source of input data can be:
 *                      String - file name
 *                      File - file object
 *                      BufferedReader - for example, file input stream
 *                      System.in - for console input
 */
fun readAllStringWithTrainSystemDescription(
    inputSource: Any? = null
): List<String> {
    val reader = when (inputSource) {
        is String -> {
            // If string provided, treat it as file path
            File(inputSource).bufferedReader()
        }

        is File -> {
            // If File object provided
            inputSource.bufferedReader()
        }

        is BufferedReader -> {
            // If BufferedReader provided (most flexible)
            inputSource
        }

        System.`in` -> {
            // read from console (stdin)
            BufferedReader(InputStreamReader(inputSource as InputStream))
        }

        else -> throw IllegalArgumentException(
            "Unsupported input source type: ${inputSource!!::class.simpleName}"
        )
    }
    val strings: MutableList<String> = mutableListOf()
    val firstLine = reader.readLine()
    strings.add(firstLine)
    val input = firstLine.split(" ")
    val numStops = input[0].toInt()
    val numTracks = input[1].toInt()

    (0 until numStops + numTracks + 1)
        .forEach { _ ->
            strings.add(reader.readLine())
        }
    return strings
}

/**
 * Function which checks if setA contains all elements from setB with O(n+m) complexity
 * Standard containsAll function has O(m*log(n)) complexity
 */
fun fastTreeSetContainsAll(setA: TreeSet<Int>, setB: TreeSet<Int>): Boolean {
    if (setB.isEmpty()) return true
    if (setA.isEmpty()) return false

    val iteratorA = setA.iterator()
    val iteratorB = setB.iterator()

    var elementA = iteratorA.next()
    var elementB = iteratorB.next()

    while (true) {
        when {
            elementA == elementB -> {
                if (!iteratorB.hasNext()) return true
                elementB = iteratorB.next()
                if (!iteratorA.hasNext()) return false
                elementA = iteratorA.next()
            }

            elementA < elementB -> {
                if (!iteratorA.hasNext()) return false
                elementA = iteratorA.next()
            }

            else -> return false
        }
    }
}