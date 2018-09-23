package pl.butajlo.androidadvanced.ui

import dagger.Binds
import dagger.Module

@Module
abstract class ActivityViewInterceptorModule {

    @Binds
    abstract fun bindDebugActivityViewInterceptor(debugActivityViewInterceptor: DebugActivityViewInterceptor): ActivityViewInterceptor
}