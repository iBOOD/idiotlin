package idiotlin

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object SetFeature: Spek({
    Feature("Set") {
        val set by memoized { mutableSetOf<String>() }

        Scenario("adding items") {
            When("adding foo") {
                set.add("foo")
            }
            Then("it should have a size of 1") {
                assertThat(set).hasSize(1)
            }
            Then("it should contain foo") {
                assertThat(set).contains("foo")
            }
        }

        Scenario("empty") {
            Then("should be empty") {
                assertThat(set).isEmpty()
            }
            Then("should throw when first is invoked") {
                assertThat{
                    set.first()
                }.isFailure().isInstanceOf(NoSuchElementException::class)
            }
        }

        Scenario("getting the first item") {
            val item = "foo"
            Given("a non-empty set")  {
                set.add(item)
            }
            lateinit var result: String
            When("getting the first item") {
                result = set.first()
            }
            Then("it should return the first item") {
                assertThat(result).isEqualTo(item)
            }
        }
    }
})
