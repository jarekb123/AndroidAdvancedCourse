package pl.butajlo.androidadvanced.ui;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;
import pl.butajlo.androidadvanced.di.ActivityScope;
import pl.butajlo.androidadvanced.lifecycle.ActivityLifecycleTask;

@Module
public abstract class NavigationModule {

    @Binds
    abstract ScreenNavigator provideScreenNavigator(DefaultScreenNavigator screenNavigator);

    @Binds
    @IntoSet
    abstract ActivityLifecycleTask bindScreenNavigatorTask(DefaultScreenNavigator screenNavigator);
}
