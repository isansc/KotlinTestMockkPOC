package com.isansc.kotlintestmockkpoc.tools

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun String.convertToDate() : Date? {
    var result:Date? = null
    val format = SimpleDateFormat("dd/MM/yyyy")

    try {
        result = format.parse(this)
    } catch (e: Exception) {
//        Log.e("ExtTest", "Exception Message: " + e.message)
    }

    return result

}