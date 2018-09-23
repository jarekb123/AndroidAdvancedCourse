package pl.butajlo.androidadvanced.trending;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import pl.butajlo.androidadvanced.base.ScreenModule;
import pl.butajlo.androidadvanced.di.ScreenScope;

@ScreenScope
@Subcomponent(modules = {ScreenModule.class,})
public interface TrendingReposComponent extends AndroidInjector<TrendingReposController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TrendingReposController> {}
}
