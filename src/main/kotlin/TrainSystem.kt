package org.example

import java.util.LinkedList
import java.util.Queue
import java.util.TreeSet

/**
 * Class which represents a train system, consists of
 * @property stops - Map of stops in the train system, where the key is the stop id
 * @property start - Start station of the train system
 * @property canCargo - Map of stops in the train system, where key is the stop id and
 *            value is the set of cargo that can be delivered to that stop.
 *            Initialized empty, will be filled during cargo simulation
 * @property hasSimulatedCargo - Flag which indicates if cargo simulation has been run,
 *            will throw an exception if cargo simulation has not been run yet but the user tries to print it
 */
class TrainSystem(private val stops: Map<Int, TrainStop>, private val start: TrainStop) {

    private val canCargo: MutableMap<Int, TreeSet<Int>> = mutableMapOf()
    private var hasSimulatedCargo = false

    companion object {
        /**
         * Static method to transform console input into a TrainSystem object
         * @param consoleInput - List of strings representing the input from the console with system description
         * @throws IllegalArgumentException if input is invalid
         */
        fun constructTrainSystemFromInput(consoleInput: List<String>): TrainSystem {
            val input = consoleInput[0].split(" ")
            val numStops = input[0].toInt()
            val numTracks = input[1].toInt()

            val stops = mutableMapOf<Int, TrainStop>()
            for (i in 0 until numStops) {
                val input = consoleInput[i + 1].split(" ")
                if (input.size != 3)
                    throw IllegalArgumentException("Invalid input number of arguments required - 3, found ${input.size}")
                val id = input[0].toInt()
                val unloads = input[1].toInt()
                val loads = input[2].toInt()
                stops[id] = TrainStop(id, unloads, loads)
            }
            for (i in 0 until numTracks) {
                val input = consoleInput[numStops + i + 1].split(" ")
                if (input.size != 2)
                    throw IllegalArgumentException("Invalid input number of arguments required - 2, found ${input.size}")
                val idStart = input[0].toInt()
                val idEnd = input[1].toInt()
                if (stops.containsKey(idStart).not())
                    throw IllegalArgumentException("Stop with id $idStart not found")
                if (stops.containsKey(idEnd).not())
                    throw IllegalArgumentException("Stop with id $idEnd not found")
                stops[idStart]!!.addChild(stops[idEnd]!!)
            }
            val startNodeId = consoleInput[numStops + numTracks + 1].toInt()
            if (stops.containsKey(startNodeId).not())
                throw IllegalArgumentException("Stop with id $startNodeId not found")
            val start = stops[startNodeId]!!
            return TrainSystem(stops, start)
        }
    }

    /**
     * Method which runs cargo simulation on the train system,
     * Based on BFS but adds a node to the queue again if the cargo that can be delivered to that node has changed
     * For optimization purposes the cargo sets are represented as TreeSets and sorted to fast access.
     * Time complexity: O(V * E), where V - number of stops, E - number of edges
     */
    fun runCargoSimulation() {
        for (stop in stops.values) {
            this.canCargo[stop.id] = TreeSet()
        }
        val stopsQueue: Queue<Pair<TrainStop, TreeSet<Int>>> = LinkedList()
        stopsQueue.add(Pair(start, TreeSet()))
        while (stopsQueue.isNotEmpty()) {
            val (stop, cargo) = stopsQueue.poll()
            for (child in stop.children) {
                val newCargo = TreeSet(cargo)
                if (stop.loads != -1)
                    newCargo.add(stop.loads)
                if (child.unloads != -1)
                    newCargo.remove(child.unloads)

                val prevCargo = this.canCargo[child.id]!!
                if (!fastTreeSetContainsAll(prevCargo,newCargo) || prevCargo.isEmpty()) {
                    this.canCargo[child.id]!!.addAll(newCargo)

                    stopsQueue.add(Pair(child, newCargo))
                }
            }
        }
        hasSimulatedCargo = true
    }

    fun printCanCargo(): String {
        if (!hasSimulatedCargo)
            throw Exception("Cargo simulation has not been run yet try running it with runCargoSimulation() method")
        return "TrainSystem of ${stops.size} stops" +
                "From start station with id ${start.id} can deliver cargo to:" +
                "${canCargo.map { "\n${it.key} ->(Cargo) ${it.value.joinToString(", ")}" }}"
    }

    override fun toString(): String {
        return "TrainSystem(AmountOfStops=${stops.size}, start_id=${start.id}," +
                "Stops=${stops.map { "\n" + it.toString() }}"
    }


}