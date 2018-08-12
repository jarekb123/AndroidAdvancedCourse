package pl.butajlo.androidadvanced.base;

import dagger.Component;
import pl.butajlo.androidadvanced.data.TestRepoServiceModule;
import pl.butajlo.androidadvanced.networking.ServiceModule;
import pl.butajlo.androidadvanced.ui.NavigationModule;

@Component(
        modules = {
                ApplicationModule.class,
                TestActivityBindingModule.class,
                TestRepoServiceModule.class,
                ServiceModule.class,
                NavigationModule.class,
})
public interface TestApplicationComponent extends ApplicationComponent {

    void inject(MyApplication application);
}
