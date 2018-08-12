package pl.butajlo.androidadvanced.home;

import com.bluelinelabs.conductor.Controller;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import pl.butajlo.androidadvanced.di.ControllerKey;
import pl.butajlo.androidadvanced.trending.TrendingReposComponent;
import pl.butajlo.androidadvanced.trending.TrendingReposController;

@Module(subcomponents = {
        TrendingReposComponent.class,
})
public abstract class TestScreenBindingModule {
    @Binds
    @IntoMap
    @ControllerKey(TrendingReposController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindTredingReposInjector(TrendingReposComponent.Builder builder);

}
