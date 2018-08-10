package pl.butajlo.androidadvanced.di;

import android.app.Activity;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjector;
import pl.butajlo.androidadvanced.base.BaseActivity;
import pl.butajlo.androidadvanced.base.MyApplication;

public class ActivityInjector {

    /**
     * Activity injectors Factory are injected by Dagger (see constructor). Every activity has own AndroidInjector,
     * because they can have different dependencies.
     * This map comes from Dagger multibinding. And the injectors are provided by methods created in ActivityBindingModule.
     *
     * TODO: Find out where these injectors come from
     */
    private final Map<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>> activityInjectors;

    /**
     * This is the cache of AndroidInjectors which are used to inject dependencies in the Activities.<br/>
     * The String key is an instanceId - look at BaseActivity class
     * @see BaseActivity
     */
    private final Map<String, AndroidInjector<? extends Activity>> cache = new HashMap<>();

    @Inject
    ActivityInjector(Map<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>> activityInjectors) {
        this.activityInjectors = activityInjectors;
    }

    /**
     * Injects dependencies to the activity
     * @param activity
     */
    void inject(Activity activity) {

        // Injecting dependencies only to activities which extends BaseActivity only
        if(!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Activity must extend BaseActivity");
        }
        else {
            // Checking if there is any Activity Injector stored in the cache (map) with the key == instanceId
            String instanceId = ((BaseActivity) activity).getInstanceId();
            if(cache.containsKey(instanceId)) {
                // If there is, cached Activity Injector injects dependencies to @activity (which are also cached)
                //noinspection unchecked
                ((AndroidInjector<Activity>) cache.get(instanceId)).inject(activity);
                return;
            }
            //noinspection unchecked
            AndroidInjector.Factory<Activity> injectorFactory =
                    (AndroidInjector.Factory<Activity>) activityInjectors.get(activity.getClass()).get();
            AndroidInjector<Activity> injector = injectorFactory.create(activity);

            // Storing the created injector to the cache and injecting dependencies to this @activity
            cache.put(instanceId, injector);
            injector.inject(activity);
        }
    }

    /**
     * Removes the cached Android Injector corresponding to the passed activity.
     *
     * Use this method when the activity is finishing.
     * Don't use it when configuration change has just occurred.
     * @param activity Activity which is destroyed
     */
    void clear(Activity activity) {
        if(!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Activity must extend BaseActivity");
        } else {
            cache.remove(((BaseActivity) activity).getInstanceId());
        }
    }

    /**
     * Gets the Activity Injector instance
     * @param context Context, eg. Activity
     * @return Activity Injector which is injected to the MyApplication
     * @see MyApplication
     */
    static ActivityInjector get(Context context) {
        return ((MyApplication) context.getApplicationContext()).getActivityInjector();
    }
}
