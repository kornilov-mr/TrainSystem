import org.example.TrainSystem
import org.example.readAllStringWithTrainSystemDescription
import java.io.File
import kotlin.test.Test

class TrainSystemCargoSimulationTests {
    private val simpleTrainSystemFile = File("src/test/resources/CargoSimulationTests/simple_train_system.txt")
    private val complexTrainSystemFile = File("src/test/resources/CargoSimulationTests/complex_train_system.txt")
    private val simpleTrainSystemWithLoop = File("src/test/resources/CargoSimulationTests/simple_train_system_with_loop.txt")
    @Test
    fun simpleTrainSystemCargoSimulationTest() {
        val expected = "TrainSystem of 5 stopsFrom start station with id 1 can deliver cargo to:[\n" +
                "1 ->(Cargo) , \n" +
                "2 ->(Cargo) 3, 4, \n" +
                "3 ->(Cargo) 1, \n" +
                "4 ->(Cargo) 1, 3, \n" +
                "5 ->(Cargo) 2, 3, 4]"
        val input = readAllStringWithTrainSystemDescription(simpleTrainSystemFile)
        val trainSystem = TrainSystem.constructTrainSystemFromInput(input)
        trainSystem.runCargoSimulation()
        println(trainSystem.printCanCargo())
        assert(trainSystem.printCanCargo() == expected)
    }
    @Test
    fun simpleTrainSystemWithLoopCargoSimulationTest() {
        val expected = "TrainSystem of 5 stopsFrom start station with id 1 can deliver cargo to:[\n" +
                "1 ->(Cargo) 1, 2, \n" +
                "2 ->(Cargo) 1, 2, \n" +
                "3 ->(Cargo) 1, 2, \n" +
                "4 ->(Cargo) 1, 2, \n" +
                "5 ->(Cargo) 1, 2, 4]"
        val input = readAllStringWithTrainSystemDescription(simpleTrainSystemWithLoop)
        val trainSystem = TrainSystem.constructTrainSystemFromInput(input)
        trainSystem.runCargoSimulation()
        assert(trainSystem.printCanCargo() == expected)
    }
    @Test
    fun complexTrainSystemCargoSimulationTest() {
        val expected = "TrainSystem of 4 stopsFrom start station with id 1 can deliver cargo to:[\n" +
                "1 ->(Cargo) 1, 2, 3, 4, \n" +
                "2 ->(Cargo) 1, 2, 3, 4, \n" +
                "3 ->(Cargo) 1, 3, 4, \n" +
                "4 ->(Cargo) 1, 2, 3, 4]"
        val input = readAllStringWithTrainSystemDescription(complexTrainSystemFile)
        val trainSystem = TrainSystem.constructTrainSystemFromInput(input)
        trainSystem.runCargoSimulation()
        assert(trainSystem.printCanCargo() == expected)
    }
}