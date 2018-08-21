package pl.butajlo.androidadvanced.details

import android.support.annotation.Nullable
import com.squareup.moshi.JsonClass
import pl.butajlo.androidadvanced.models.Contributor

@JsonClass(generateAdapter = true)
data class  ContributorState @JvmOverloads constructor(
        val loading: Boolean,
        val contributors: List<Contributor>? = null,
        val errorRes: Int? = null
)