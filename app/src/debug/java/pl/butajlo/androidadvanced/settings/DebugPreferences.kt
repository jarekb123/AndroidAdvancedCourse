package pl.butajlo.androidadvanced.settings

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugPreferences @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("debug_settings", Context.MODE_PRIVATE)

    companion object {
        const val MOCK_RESPONSES_KEY = "mock_responses"
    }

    fun useMockResponsesEnabled() = sharedPreferences.getBoolean(MOCK_RESPONSES_KEY, false)

    fun setUseMockResponses(useMockResponses: Boolean) = sharedPreferences.edit().putBoolean(MOCK_RESPONSES_KEY, useMockResponses).apply()
}