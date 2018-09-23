package pl.butajlo.androidadvanced.ui

import dagger.Module
import dagger.Provides

@Module
abstract class ActivityViewInterceptorModule {

    companion object {
        @Provides
        @JvmStatic
        fun provideActivityViewInterceptor() = ActivityViewInterceptor.DEFAULT
    }


}
