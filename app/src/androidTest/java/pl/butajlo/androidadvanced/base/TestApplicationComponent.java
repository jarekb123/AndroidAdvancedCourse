package pl.butajlo.androidadvanced.base;

import javax.inject.Singleton;

import dagger.Component;
import pl.butajlo.androidadvanced.data.TestRepoServiceModule;
import pl.butajlo.androidadvanced.networking.ServiceModule;
import pl.butajlo.androidadvanced.trending.TredingRepoControllerTest;
import pl.butajlo.androidadvanced.ui.NavigationModule;

@Singleton
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

    void inject(TredingRepoControllerTest tredingRepoControllerTest);
}
