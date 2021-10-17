package com.los3molineros.laligasantander.common

import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.los3molineros.laligasantander.common.AppConstants.TAG_LOG
import java.text.SimpleDateFormat
import java.util.*

object CommonFunctions {

    fun debugLog(tag: String = TAG_LOG, description: String) {
        Log.d(tag, description)
    }

    fun View.blink(
        times: Int = Animation.INFINITE,
        duration: Long = 500L,
        offset: Long = 20L,
        minAlpha: Float = 0.0f,
        maxAlpha: Float = 1.0f,
        repeatMode: Int = Animation.REVERSE
    ) {
        startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
            it.duration = duration
            it.startOffset = offset
            it.repeatMode = repeatMode
            it.repeatCount = times
        })
    }

    fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }

    fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }

}