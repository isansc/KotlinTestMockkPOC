package com.isansc.kotlintestmockkpoc

import com.isansc.kotlintestmockkpoc.tools.TestableService
import com.isansc.kotlintestmockkpoc.tools.convertToDate
import io.kotlintest.shouldBe
import io.kotlintest.specs.ExpectSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import java.text.SimpleDateFormat

class MockkExampleTest : ExpectSpec({

    context("Given a simple service"){
        // given
        val service = mockk<TestableService>()
        every { service.getDataFromDb("Expected Param") } returns "Expected Output"

        expect("the output should be the same as mocked") {
            // when
            val result = service.getDataFromDb("Expected Param")

            // then
            verify { service.getDataFromDb("Expected Param") }
            "Expected Output" shouldBe result
        }
    }

    context("Given an extension function from a String"){

        mockkStatic("com.isansc.kotlintestmockkpoc.tools.StringExtensionKt")

        val txt: String = "10/12/2019"

        every { txt.convertToDate() } returns SimpleDateFormat("dd/MM/yyyy").parse("08/10/1987")

        expect("the output should be the same as mocked") {
            // when
            val result = txt.convertToDate()

            // then
            verify { txt.convertToDate() }
            result shouldBe SimpleDateFormat("dd/MM/yyyy").parse("08/10/1987")
        }
    }
})