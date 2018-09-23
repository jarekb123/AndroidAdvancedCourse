package pl.butajlo.androidadvanced.networking

import android.content.Context
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

fun getResponseString(context: Context, method: String, endpointParts: Array<String>): String? {
    return try {
        var currentPath = "mock"
        var mockList = HashSet(context.assets.list(currentPath).asList())

        endpointParts.forEach {
            if(mockList.contains(it)) {
                currentPath = "$currentPath/$it"
                mockList = HashSet(context.assets.list(currentPath).asList())
            }
        }

        var finalPath: String? = null
        mockList.forEach {
            if(it.contains(method.toLowerCase())) {
                finalPath = "$currentPath/$it"
                return@forEach
            }
        }

        return responseFromPath(context, finalPath ?: return null)
    } catch (e: IOException) {
        Timber.e(e, "Error loading mock responses from assets")

        null
    }
}

private fun responseFromPath(context: Context, path: String): String {
    val sb = StringBuilder()
    val assetStream = context.assets.open(path)
    assetStream.use {
        BufferedReader(InputStreamReader(it)).use {
            var line = it.readLine()
            while(line != null) {
                sb.append(line)
                line = it.readLine()
            }
        }
    }
    return sb.toString()

}