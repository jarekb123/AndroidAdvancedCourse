package pl.butajlo.androidadvanced.ui

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import pl.butajlo.androidadvanced.lifecycle.ActivityLifecycleTask

@Module
abstract class TestNavigationModule {

    @Binds
    abstract fun bindScreenNavigator(screenNavigator: TestScreenNavigator) : ScreenNavigator

    @Binds
    @IntoSet
    abstract fun bindScreenNavigatorTask(screenNavigator: TestScreenNavigator): ActivityLifecycleTask
}