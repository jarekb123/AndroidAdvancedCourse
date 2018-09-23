package pl.butajlo.androidadvanced.ui

import dagger.Module
import dagger.Provides

@Module
class TestActivityViewInterceptorModule {

    @Provides
    fun provideActivityViewInterceptor() = ActivityViewInterceptor.DEFAULT

}