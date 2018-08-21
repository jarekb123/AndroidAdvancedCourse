package pl.butajlo.androidadvanced.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pl.butajlo.androidadvanced.models.Repo

@JsonClass(generateAdapter = true)
data class TrendingReposResponse(
        @Json(name = "items") val repos: List<Repo>
)