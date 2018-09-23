package pl.butajlo.androidadvanced.base

import dagger.Module
import dagger.multibindings.Multibinds
import pl.butajlo.androidadvanced.lifecycle.ScreenLifecycleTask

@Module
abstract class ScreenModule {

    @Multibinds
    abstract fun bindScreenLifecycleTasks(): Set<ScreenLifecycleTask>

}