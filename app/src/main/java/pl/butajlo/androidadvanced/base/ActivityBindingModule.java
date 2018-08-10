package pl.butajlo.androidadvanced.base;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import pl.butajlo.androidadvanced.home.MainActivity;
import pl.butajlo.androidadvanced.home.MainActivityComponent;

@Module(subcomponents = {
        MainActivityComponent.class
})
public abstract class ActivityBindingModule {

    /**
     * This method provides the injector to the MainActivity
     * @param builder
     * @return
     */
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> provideMainActivityInjector(MainActivityComponent.Builder builder);
}
