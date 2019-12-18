package idiotlin

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class Calculator {
    fun add(x: Int, y: Int) = x + y
}

object CalculatorSpec: Spek({
    describe("A calculator") {
        val calculator by memoized { Calculator() }

        describe("addition") {
            it("returns the sum of its arguments") {
                assertThat(calculator.add(1, 2)).isEqualTo(3)
            }
        }
    }
})

