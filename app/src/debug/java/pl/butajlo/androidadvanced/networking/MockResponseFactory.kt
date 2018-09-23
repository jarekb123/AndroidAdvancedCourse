package pl.butajlo.androidadvanced.networking

import android.content.Context
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named

class MockResponseFactory @Inject constructor(private val context: Context, @Named("base_url") baseUrl: String) {
    private val startIndex = baseUrl.length

    fun getMockResponse(request: Request): String? {
        val endpointParts = getEndpoint(request).split("/").toTypedArray()
        return getResponseString(context, request.method(), endpointParts)
    }

    private fun getEndpoint(request: Request): String {
        val url = request.url().toString()
        val queryParamStart = url.indexOf("?")

        return if (queryParamStart == -1) {
            url.substring(startIndex)
        } else {
            url.substring(startIndex, queryParamStart)
        }
    }
}