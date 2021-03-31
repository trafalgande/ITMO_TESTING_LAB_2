import Utility.Companion.replaceWith
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Path


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTest {

    @BeforeAll
    fun prepareCSV() {
        Files.deleteIfExists(Path.of("out.csv"))
        Files.createFile(Path.of("out.csv"))
        csvWriter().writeAll(listOf(listOf("x", "f(x)")), "out.csv", append = true)

    }

    private fun actualFunctionValue(x: Double): Double {
        val actualFunctions: HashMap<String, () -> Double> = hashMapOf(
            "sin" to { ActualFunctionImplementation.sin(x) },
            "csc" to { ActualFunctionImplementation.csc(x) },
            "sec" to { ActualFunctionImplementation.sec(x) },
            "ln" to { ActualFunctionImplementation.ln(x) },
            "log_2" to { ActualFunctionImplementation.log_2(x) },
            "log_3" to { ActualFunctionImplementation.log_3(x) },
            "log_5" to { ActualFunctionImplementation.log_5(x) },
            "log_10" to { ActualFunctionImplementation.log_10(x) }
        )
        return Utility.f(x, actualFunctions)
    }


    private fun ArrayList<ArrayList<Double>>.appendRow(row: ArrayList<Double>) : ArrayList<ArrayList<Double>> {
        this.add(row)
        return this
    }


    private fun applyToCSV(rows: ArrayList<ArrayList<Double>>) {
        csvWriter().writeAll(rows, "out.csv", append = true)
    }

    @ParameterizedTest
    @CsvSource(
        "-0.5, 0.000001",
        "0.5, 0.000001",
        "0.2, 0.000001"
    )
    fun `custom sin with actual function`(x: Double, eps: Double) {
        assertEquals(ActualFunctionImplementation.sin(x), CustomFunctionImplementation.sin(x, eps), eps)
    }

    @ParameterizedTest
    @CsvSource(
        "-0.5, 0.000001",
        "0.5, 0.000001",
        "0.2, 0.000001"
    )
    fun `custom ln with actual function`(x: Double, eps: Double) {
        assertEquals(ActualFunctionImplementation.ln(x), CustomFunctionImplementation.ln(x, eps), eps)
    }


    @ParameterizedTest
    @CsvSource(
        "0.5, 0.000001",
        "0.2, 0.000001"
    )
    fun `Integration test for function with gradually increasing number of custom functions implementations`(
        x: Double,
        eps: Double
    ) {
        var actualFunctions: HashMap<String, () -> Double> = hashMapOf(
            "sin" to { ActualFunctionImplementation.sin(x) },
            "csc" to { ActualFunctionImplementation.csc(x) },
            "sec" to { ActualFunctionImplementation.sec(x) },
            "ln" to { ActualFunctionImplementation.ln(x) },
            "log_2" to { ActualFunctionImplementation.log_2(x) },
            "log_3" to { ActualFunctionImplementation.log_3(x) },
            "log_5" to { ActualFunctionImplementation.log_5(x) },
            "log_10" to { ActualFunctionImplementation.log_10(x) }
        )

        var rows: ArrayList<ArrayList<Double>> = ArrayList()

        for (i in 0..actualFunctions.size) {
            when (i) {
                1 -> actualFunctions = actualFunctions.replaceWith("sin") { CustomFunctionImplementation.sin(x, eps) }
                2 -> actualFunctions = actualFunctions.replaceWith("csc") { CustomFunctionImplementation.csc(x, eps) }
                3 -> actualFunctions = actualFunctions.replaceWith("sec") { CustomFunctionImplementation.sec(x, eps) }
                4 -> actualFunctions = actualFunctions.replaceWith("ln") { CustomFunctionImplementation.ln(x, eps) }
                5 -> actualFunctions = actualFunctions.replaceWith("log_2") { CustomFunctionImplementation.log_2(x, eps) }
                6 -> actualFunctions = actualFunctions.replaceWith("log_3") { CustomFunctionImplementation.log_3(x, eps) }
                7 -> actualFunctions = actualFunctions.replaceWith("log_5") { CustomFunctionImplementation.log_5(x, eps) }
                8 -> actualFunctions = actualFunctions.replaceWith("log_10") { CustomFunctionImplementation.log_10(x, eps) }
            }
            assertEquals(actualFunctionValue(x), Utility.f(x, actualFunctions), 0.00001, "Failed on $i stage")
            rows = rows.appendRow(arrayListOf(x, Utility.f(x, actualFunctions)))
        }
        applyToCSV(rows)
    }
}