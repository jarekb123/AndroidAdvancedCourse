package pl.butajlo.androidadvanced.base;

import javax.inject.Singleton;

import dagger.Component;
import pl.butajlo.androidadvanced.data.RepoRepository;
import pl.butajlo.androidadvanced.data.RepoRequester;
import pl.butajlo.androidadvanced.data.TestRepoService;
import pl.butajlo.androidadvanced.data.TestRepoServiceModule;
import pl.butajlo.androidadvanced.networking.ServiceModule;
import pl.butajlo.androidadvanced.trending.TredingRepoControllerTest;
import pl.butajlo.androidadvanced.ui.NavigationModule;
import pl.butajlo.androidadvanced.ui.TestActivityViewInterceptorModule;
import pl.butajlo.androidadvanced.ui.TestNavigationModule;
import pl.butajlo.androidadvanced.ui.TestScreenNavigator;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                TestActivityBindingModule.class,
                TestRepoServiceModule.class,
                ServiceModule.class,
                TestNavigationModule.class,
                TestActivityViewInterceptorModule.class
})
public interface TestApplicationComponent extends ApplicationComponent {

    void inject(MyApplication application);

    void inject(TredingRepoControllerTest tredingRepoControllerTest);

    TestScreenNavigator screenNavigator();
    TestRepoService repoService();
    RepoRepository repoRepository();
}
