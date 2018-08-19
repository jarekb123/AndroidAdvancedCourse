package pl.butajlo.androidadvanced.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

@JsonClass(generateAdapter = true)
data class Repo(
        val id: Long,
        val name: String,
        val description: String,
        val owner: User,
        @Json(name = "stargazers_count") val stargazersCount: Long,
        @Json(name = "forks_count") val forksCount: Long,
        @Json(name = "contributors_url") val contributorsUrl: String,
        @Json(name = "created_at") val createdDate: ZonedDateTime,
        @Json(name = "updated_at") val updatedAt: ZonedDateTime)