package com.isansc.kotlintestmockkpoc

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.ExpectSpec
import io.kotlintest.tables.row

class RowTest : ExpectSpec({
    context("having a table with three columns with values"){
        expect("maximum of two numbers in the third column") {
            forall(
                row(1, 5, 5),
                row(1, 0, 1),
                row(0, 0, 0)
            ) { a, b, max ->
                Math.max(a, b) shouldBe max
            }
        }
    }
})