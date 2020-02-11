package com.isansc.kotlintestmockkpoc

import br.com.moip.creditcard.Brands
import br.com.moip.validators.CreditCard
import com.isansc.kotlintestmockkpoc.tools.TestableService
import com.isansc.kotlintestmockkpoc.tools.convertToDate
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.shouldBe
import io.kotlintest.specs.ExpectSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import java.text.SimpleDateFormat

class MockkExampleTest : ExpectSpec({

    val SEPTEMBER_ELEVEN = SimpleDateFormat("dd/MM/yyyy").parse("11/09/2001")

    context("Given a simple service"){
        // given
        val service = mockk<TestableService>()
        every { service.getDataFromDb("Expected Param") } returns "an Expected Output"

        expect("the output should be the same as mocked") {
            // when
            val data = service.getDataFromDb("Expected Param")

            val result = "I would like to receive $data"

            // then
            verify { service.getDataFromDb("Expected Param") }
            result shouldBe "I would like to receive Expected Output"
        }
    }

    context("Given an extension function from a String"){

        mockkStatic("com.isansc.kotlintestmockkpoc.tools.StringExtensionKt")

        val txt = "05/02/2020"

        every { txt.convertToDate() } returns SimpleDateFormat("dd/MM/yyyy").parse("08/10/1987")

        expect("the output should be the same as mocked") {
            // when
            val date = txt.convertToDate()

            val result = date?.before(SEPTEMBER_ELEVEN)

            // then
            verify { txt.convertToDate() }
            result?.shouldBeTrue()
        }
    }

    context("Given a java class"){

//        mockkConstructor(CreditCard::class)
        val card = mockk<CreditCard>("1234567891234567")

        every { card.brand } returns Brands.AMERICAN_EXPRESS

        expect("the output in a Kotlin test should be the same as mocked") {
            // when
            val result = card.brand

            // then
            verify { card.brand } // Used to verify if the mocked method was called at least once
            result shouldBe Brands.AMERICAN_EXPRESS
        }
    }
})