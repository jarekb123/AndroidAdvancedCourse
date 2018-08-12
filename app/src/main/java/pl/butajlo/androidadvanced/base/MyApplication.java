package pl.butajlo.androidadvanced.base;

import android.app.Application;

import javax.inject.Inject;

import pl.butajlo.androidadvanced.BuildConfig;
import pl.butajlo.androidadvanced.di.ActivityInjector;
import timber.log.Timber;

public class MyApplication extends Application {

    protected ApplicationComponent component;

    @Inject
    ActivityInjector activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        component = initComponent();
        component.inject(this);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    protected ApplicationComponent initComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ActivityInjector getActivityInjector() {
        return activityInjector;
    }
}
