package org.example.trainSystem.display

import org.example.trainSystem.TrainStop
import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.view.Viewer
import java.io.File


/**
 * Class which displays the train system graph and can start the thread for updating the graph with the algorithm steps
 * @param stops - Map of stops in the train system, where the key is the stop id
 * @param start - Start station of the train system
 * @param algorithmSteps - List of steps taken during cargo simulation, contains pairs of stop ids
 *            and cargo that can be delivered to that stop
 * @property cssFile file with CSS styling for the graph
 * @property graph simple graph object to display the train system
 * @property algorithmUpdateThread thread which updates the graph with the algorithm steps
 */
class TrainSystemDisplay(
    private val stops: Map<Int, TrainStop>,
    private val start: TrainStop,
    algorithmSteps: MutableList<Pair<Int, List<Int>>>
) {

    private val cssFile = File("src/main/resources/graph_styling_file.css")
    private val graph: SingleGraph = SingleGraph("Train System")
    private val algorithmUpdateThread:AlgorithmUpdateThread = AlgorithmUpdateThread(algorithmSteps, graph)
    init{
        System.setProperty("org.graphstream.ui", "swing")
        graph.setAttribute("ui.stylesheet", cssFile.readText())
        graph.setAttribute("ui.quality")
        graph.setAttribute("ui.antialias")
    }
    fun displayTrainSystem() {
        for (stop in stops.values) {
            graph.addNode(stop.id.toString())
            graph.getNode(stop.id.toString()).setAttribute("ui.label", "${stop.id}\n(unloads = ${stop.unloads}, loads = ${stop.loads})")
        }
        graph.getNode(start.id.toString()).setAttribute("ui.label", "Start with id=${start.id}\n(unloads = ${start.unloads}, loads = ${start.loads})")

        for (stop in stops.values){
            for (child in stop.children){
                graph.addEdge(stop.id.toString() + "-" + child.id.toString(), stop.id.toString(), child.id.toString(),true)
            }
        }
        val viewer = graph.display()
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT)
        println(toString())
    }
    fun startUpdateThread(){
        algorithmUpdateThread.start()
    }
}