package com.isansc.kotlintestmockkpoc

import io.kotlintest.shouldBe
import io.kotlintest.specs.ExpectSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class MockkExampleTest : ExpectSpec({

    context("Given a service"){
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
})