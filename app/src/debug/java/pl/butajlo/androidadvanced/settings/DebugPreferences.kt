package pl.butajlo.androidadvanced.settings

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugPreferences @Inject constructor(context: Context) {

    lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val MOCK_RESPONSES_KEY = "mock_responses"
    }

    init {
        sharedPreferences = context.getSharedPreferences("debug_settings", Context.MODE_PRIVATE)
    }

    fun useMockResponsesEnabled() = sharedPreferences.getBoolean(MOCK_RESPONSES_KEY, false)

    fun setUseMockResponses(useMockResponses: Boolean) = sharedPreferences.edit().putBoolean(MOCK_RESPONSES_KEY, useMockResponses).apply()
}