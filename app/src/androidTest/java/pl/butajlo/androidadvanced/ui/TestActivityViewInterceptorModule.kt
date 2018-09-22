package pl.butajlo.androidadvanced.ui

import dagger.Module
import dagger.Provides

@Module
abstract class TestActivityViewInterceptorModule {

    companion object {
        @JvmStatic
        @Provides
        fun provideActivityViewInterceptor() = ActivityViewInterceptor.DEFAULT
    }
}