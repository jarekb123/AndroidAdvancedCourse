package pl.butajlo.androidadvanced.models

import android.support.annotation.Nullable
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.ZonedDateTime

class ZoneDateTimeAdapter {
    
    @FromJson
    fun fromJson(json: String): ZonedDateTime = ZonedDateTime.parse(json)
    
    @ToJson
    fun toJson(zdt: ZonedDateTime?) = zdt.toString()
}