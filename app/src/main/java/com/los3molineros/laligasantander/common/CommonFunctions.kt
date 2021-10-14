package com.los3molineros.laligasantander.common

import android.util.Log
import com.los3molineros.laligasantander.common.AppConstants.TAG_LOG

object CommonFunctions {

    fun debugLog(tag: String = TAG_LOG, description: String) {
        Log.d(tag, description)
    }


}