package pl.butajlo.androidadvanced.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(val id: Long, val login: String)