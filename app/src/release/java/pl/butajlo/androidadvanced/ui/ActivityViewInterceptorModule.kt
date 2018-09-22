package pl.butajlo.androidadvanced.ui

import dagger.Module
import dagger.Provides

@Module
abstract class ActivityViewInterceptorModule {

    companion object {
        @Provides
        fun provideActivityViewInterceptor() = ActivityViewInterceptor.DEFAULT
    }


}
