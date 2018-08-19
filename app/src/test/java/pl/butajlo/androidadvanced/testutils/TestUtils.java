package pl.butajlo.androidadvanced.testutils;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import pl.butajlo.androidadvanced.models.AdapterFactory;
import pl.butajlo.androidadvanced.models.ZoneDateTimeAdapter;

public class TestUtils {

    private static TestUtils INSTANCE = new TestUtils();

    private static final Moshi TEST_MOSHI = initializeMoshi();


    private TestUtils() {}

    public static <T> T loadJson(String path, Type type) {
        try {
            String json = getFileString(path);
            //noinspection unchecked
            return (T) TEST_MOSHI.adapter(type).fromJson(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialize: " + path + " into type " + type);
        }
    }

    public static <T> T loadJson(String path, Class<T> tClass) {
        try {
            String json = getFileString(path);
            //noinspection unchecked
            return (T) TEST_MOSHI.adapter(tClass).fromJson(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialize: " + path + " into type " + tClass);
        }
    }

    private static String getFileString(String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    INSTANCE.getClass().getClassLoader().getResourceAsStream(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read from resource at: " + path);
        }

    }

    private static Moshi initializeMoshi() {
        return new Moshi.Builder()
                .add(new KotlinJsonAdapterFactory())
                .add(new ZoneDateTimeAdapter())
                .add(AdapterFactory.create())
                .build();
    }

}
