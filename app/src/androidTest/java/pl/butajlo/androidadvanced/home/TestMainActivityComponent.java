package pl.butajlo.androidadvanced.home;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import pl.butajlo.androidadvanced.di.ActivityScope;

@ActivityScope
@Subcomponent(modules = {
        TestScreenBindingModule.class
})
public interface TestMainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {

        // Overriding prevents from memory leaks. You will not be able to inject MainActivity anywhere
        @Override
        public void seedInstance(MainActivity instance) {

        }
    }
}
