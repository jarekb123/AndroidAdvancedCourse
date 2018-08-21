package pl.butajlo.androidadvanced.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Contributor(
        val id: Long,
        val login: String,
        @Json(name = "avatar_url") val avatarUrl: String
)