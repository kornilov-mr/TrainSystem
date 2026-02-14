package org.example.trainSystem.display

import org.graphstream.graph.implementations.SingleGraph
import java.util.TreeSet
import kotlin.collections.set

/**
 * Thread which updates the graph with the algorithm steps
 * Using CSS classes to change the color of the nodes
 * @param algorithmSteps - List of steps taken during cargo simulation, contains pairs of stop ids
 *            and cargo that can be delivered to that stop
 * @param graph - Graph to update
 * @property currentCanCargo - Map of stops in the train system, where key is the stop id and value is the set of
 *            cargo that can be delivered to that stop currently
 */
class AlgorithmUpdateThread(
    private val algorithmSteps: MutableList<Pair<Int, List<Int>>>,
    private val graph: SingleGraph
) : Thread() {
    private val currentCanCargo: MutableMap<Int, TreeSet<Int>> = mutableMapOf()

    override fun run() {
        sleep(1000)
        for ((id, cargoChangeList) in algorithmSteps) {
            graph.getNode(id.toString()).setAttribute("ui.class", "active")
            sleep(1000)

            if (!currentCanCargo.containsKey(id))
                currentCanCargo[id] = TreeSet()
            currentCanCargo[id]!!.addAll(cargoChangeList)

            var prevLabel = graph.getNode(id.toString()).getAttribute("ui.label").toString()
            if (prevLabel.contains("\nCargo:"))
                prevLabel = prevLabel.substring(0, prevLabel.indexOf("\nCargo:"))
            graph.getNode(id.toString())
                .setAttribute("ui.label", "$prevLabel\nCargo: ${currentCanCargo[id]!!.joinToString(", ")}")
            sleep(1000)
            graph.getNode(id.toString()).setAttribute("ui.class", "finished")

        }
    }
}