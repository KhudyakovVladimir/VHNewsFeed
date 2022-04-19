package com.khudyakovvladimir.vhnewsfeed.utils

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import java.net.URL
import java.util.regex.Pattern

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

    fun isConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.d("TAG", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.d("TAG", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.d("TAG", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun checkURL(input: CharSequence): Boolean {
        if (TextUtils.isEmpty(input)) {
            return false
        }
        val pattern: Pattern = Patterns.WEB_URL
        var isURL: Boolean = pattern.matcher(input).matches()
        if (!isURL) {
            val urlString = input.toString() + ""
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    URL(urlString)
                    isURL = true
                } catch (e: Exception) {
                }
            }
        }
        return isURL
    }
}