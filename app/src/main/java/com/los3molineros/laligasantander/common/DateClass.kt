package com.los3molineros.laligasantander.common

import com.los3molineros.laligasantander.common.CommonFunctions.debugLog
import java.text.DateFormat
import java.util.*

class DateClass() {

    fun getUTCDate(): Date? {
        val dateFormat = DateFormat.getDateTimeInstance()
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val gmtTime = dateFormat.format(Date())
        return dateFormat.parse(gmtTime)
    }

    fun minutesBetweenNowAndDate(date: Date): Long {
        var difference: Long = 0

        getUTCDate()?.let {
            difference = (it.time - date.time) / 60000
        }

        debugLog(description = "$difference")

        return difference
    }


}