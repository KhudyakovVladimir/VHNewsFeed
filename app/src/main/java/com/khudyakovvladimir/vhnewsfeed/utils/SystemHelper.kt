package com.khudyakovvladimir.vhnewsfeed.utils

import android.content.Context
import android.content.res.Configuration

class SystemHelper {

    fun checkTheme(context: Context): Boolean {
        when(context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                return true
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                return false
            }
        }
        return true
    }
}