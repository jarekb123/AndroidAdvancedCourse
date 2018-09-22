package pl.butajlo.androidadvanced.ui

import dagger.Binds
import dagger.Module

@Module
abstract class TestNavigationModule {

    @Binds
    abstract fun bindScreenNavigator(screenNavigator: TestScreenNavigator) : ScreenNavigator
}