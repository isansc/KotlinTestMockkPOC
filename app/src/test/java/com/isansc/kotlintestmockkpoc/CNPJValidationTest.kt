package com.isansc.kotlintestmockkpoc

import com.isansc.kotlintestmockkpoc.tools.CNPJ
import com.isansc.kotlintestmockkpoc.tools.isValid
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.BehaviorSpec

class CNPJValidationTest: BehaviorSpec ({

    Given("a valid CNPJ number"){
        val validCNPJ = "08872788000138"

        When("the CNPJ number is valid"){
            val cnpj = CNPJ(validCNPJ)

            Then("The validator should return true"){
               cnpj.isValid().shouldBeFalse()
            }
        }
    }

    Given("an invalid CNPJ number"){
        val invalidCNPJ = "08872788000"

        When("the CNPJ number is invalid"){
            val cnpj = CNPJ(invalidCNPJ)

            Then("The validator should return false"){
                cnpj.isValid().shouldBeFalse()
            }
        }
    }

})