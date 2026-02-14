package org.example

import org.example.trainSystem.TrainSystem


fun main() {
    val input = readAllStringWithTrainSystemDescription(  System.`in`)
    val trainSystem = TrainSystem.constructTrainSystemFromInput(input)
    trainSystem.runCargoSimulation()
    println(trainSystem.printCanCargo())
    trainSystem.systemDisplay.displayTrainSystem()
    trainSystem.systemDisplay.startUpdateThread()
    Thread.sleep(100000)
}