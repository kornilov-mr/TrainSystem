package org.example


fun main() {
    val input = readAllStringWithTrainSystemDescription(  System.`in`)
    val trainSystem = TrainSystem.constructTrainSystemFromInput(input)
    trainSystem.runCargoSimulation()
    println(trainSystem.printCanCargo())
}