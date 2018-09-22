package pl.butajlo.androidadvanced.ui;

import dagger.Binds;
import dagger.Module;
import pl.butajlo.androidadvanced.di.ActivityScope;

@Module
public abstract class NavigationModule {

    @Binds
    @ActivityScope
    abstract ScreenNavigator provideScreenNavigator(DefaultScreenNavigator screenNavigator);
}
