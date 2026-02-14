import org.example.trainSystem.TrainSystem
import org.example.readAllStringWithTrainSystemDescription
import java.io.File
import kotlin.test.Test
import kotlin.test.assertFailsWith

class TrainSystemInputTests {
    private val simpleTrainSystemFile = File("src/test/resources/InputExceptionsTests/simple_train_system.txt")
    private val argumentExceptionTrainSystem = File("src/test/resources/InputExceptionsTests/argument_exception_train_system.txt")
    @Test
    fun noExceptionSimpleTrainSystemTest() {
        val expectedSystem = "TrainSystem(AmountOfStops=3, start_id=1,Stops=[\n" +
                "1=TrainStop(id=1, unloads=-1, loads=1), \n" +
                "2=TrainStop(id=2, unloads=1, loads=2), \n" +
                "3=TrainStop(id=3, unloads=-1, loads=3)]"
        val input = readAllStringWithTrainSystemDescription(simpleTrainSystemFile)
        val trainSystem = TrainSystem.constructTrainSystemFromInput(input)
        assert(trainSystem.toString() == expectedSystem)
    }
    @Test
    fun argumentExceptionTrainSystemTest() {
        val input = readAllStringWithTrainSystemDescription(argumentExceptionTrainSystem)
        val exception = assertFailsWith<IllegalArgumentException> {
            TrainSystem.constructTrainSystemFromInput(input)
        }
        assert(exception.message == "Invalid input number of arguments required - 3, found 2")
    }
}