package org.example.trainSystem


/**
 * Class which represents a node in the train system
 * @property id - name of the station, used as a hash key
 * @property unloads - Cargo id, which will be taken from the train on arrival
 * @property loads - Cargo id, which will be put on the train on departure
 * @property children - List of children nodes to traverse the graph
 */
class TrainStop(val id: Int, val unloads: Int, val loads: Int) {
    val children: MutableList<TrainStop> = mutableListOf()

    fun addChild(child: TrainStop) {
        children.add(child)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TrainStop

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
    override fun toString(): String {
        return "TrainStop(id=$id, unloads=$unloads, loads=$loads)"
    }

}