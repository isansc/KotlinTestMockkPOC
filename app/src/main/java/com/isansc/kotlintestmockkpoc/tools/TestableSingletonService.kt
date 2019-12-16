package com.isansc.kotlintestmockkpoc.tools

/**
 * Example to test Mockk, from the page https://www.baeldung.com/kotlin-mockk
 */
object TestableSingletonService {
    fun getDataFromDb(testParameter: String): String {
        // query database and return matching value
        return "something"
    }

    fun doSomethingElse(testParameter: String): String {
        return "I don't want to!"
    }

}