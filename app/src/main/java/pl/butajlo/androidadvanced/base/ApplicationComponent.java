package pl.butajlo.androidadvanced.base;

import javax.inject.Singleton;

import dagger.Component;
import pl.butajlo.androidadvanced.data.RepoServiceModule;
import pl.butajlo.androidadvanced.networking.ServiceModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        ServiceModule.class,
        RepoServiceModule.class,

})
public interface ApplicationComponent {

    void inject(MyApplication myApplication);
}
