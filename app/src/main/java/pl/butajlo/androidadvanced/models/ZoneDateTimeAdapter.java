package pl.butajlo.androidadvanced.models;

import android.support.annotation.Nullable;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.threeten.bp.ZonedDateTime;

public class ZoneDateTimeAdapter {

    @FromJson
    ZonedDateTime fromJson(String json) {
        return ZonedDateTime.parse(json);
    }

    @ToJson
    String toJson(@Nullable ZonedDateTime zdt) {
        return zdt != null ? zdt.toString() : null;
    }

}
