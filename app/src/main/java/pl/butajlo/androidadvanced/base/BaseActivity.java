package pl.butajlo.androidadvanced.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.di.Injector;
import pl.butajlo.androidadvanced.di.ScreenInjector;
import pl.butajlo.androidadvanced.lifecycle.ActivityLifecycleTask;
import pl.butajlo.androidadvanced.ui.ActivityViewInterceptor;
import pl.butajlo.androidadvanced.ui.RouterProvider;
import pl.butajlo.androidadvanced.ui.ScreenNavigator;


public abstract class BaseActivity extends AppCompatActivity implements RouterProvider
{
    private static String INSTANCE_ID_KEY = "instance_id";

    /**
     * Instance ID is needed for Activity Injectors, because we want to
     * retain component across configuration changes
     */
    private String instanceId;

    /** Router is responsible for back stack management in Conductor. Router objects are attached to
     * Activity/containing ViewGroup pairs. Each router holds reference to a list of controllers in the order that
     * they are pushed into the back stack.
     */
    private Router router;

    @Inject
    ScreenInjector screenInjector;

    @Inject
    ScreenNavigator screenNavigator;

    @Inject
    ActivityViewInterceptor activityViewInterceptor;

    @Inject
    Set<ActivityLifecycleTask> activityLifecycleTasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /* We're checking if the activity was actually created before (if yes, the Bundle won't be NULL)
           So if the bundle is not null we want to read our instanceId from the Bundle */
        if(savedInstanceState != null) {
            instanceId = savedInstanceState.getString(INSTANCE_ID_KEY);
        } else {
            instanceId = UUID.randomUUID().toString();
        }
        Injector.inject(this); // Injecting the dependencies with the Activity Injector
        activityViewInterceptor.setContentView(this, layoutRes());

        /* In this course the teacher is using Conductor instead of Fragments.
           The following section is used by Conductor */
        ViewGroup screenContainer = findViewById(R.id.screen_container);
        if(screenContainer == null) {
            throw new NullPointerException("Activity must have a view with id: screen_container");
        }
        router = Conductor.attachRouter(this, screenContainer, savedInstanceState);
        monitorBackStack();
        activityLifecycleTasks.forEach(task -> task.onCreate(this));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityLifecycleTasks.forEach(task -> task.onStart(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityLifecycleTasks.forEach(task -> task.onPause(this));
    }

    @Override
    protected void onPause() {
        activityLifecycleTasks.forEach(task -> task.onPause(this));
        super.onPause();
    }

    @Override
    protected void onStop() {
        activityLifecycleTasks.forEach(task -> task.onStop(this));
        super.onStop();
    }

    /**
     * Saving instanceId to Bundle
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_ID_KEY, instanceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isFinishing()) { // If the activity is finishing we can remove the stored Android Injector
                            // in the cache in ActivityInjector
            Injector.clearComponent(this);
        }
        activityViewInterceptor.clear();
        activityLifecycleTasks.forEach(task -> task.onDestroy(this));
    }

    @Override
    public void onBackPressed() {
        if(!screenNavigator.pop()) { // If the screen navigator doesn't handle the onBackPress call the super method
            super.onBackPressed();
        }
    }

    @NotNull
    @Override
    public Router getRouter() {
        return router;
    }



    @LayoutRes
    protected abstract int layoutRes();

    /**
     * Returns the initial screen displayed on the activity
     * @return Initial screen
     */
    @NotNull
    @Override
    public abstract Controller initialScreen();

    private void monitorBackStack() {
        router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener() {
            @Override
            public void onChangeStarted(@Nullable Controller to,
                                        @Nullable Controller from,
                                        boolean isPush,
                                        @NonNull ViewGroup container,
                                        @NonNull ControllerChangeHandler handler) {

            }

            @Override
            public void onChangeCompleted(@Nullable Controller to,
                                          @Nullable Controller from,
                                          boolean isPush,
                                          @NonNull ViewGroup container,
                                          @NonNull ControllerChangeHandler handler) {
                if(!isPush && from != null) {
                    Injector.clearComponent(from);
                }
            }
        });
    }

    public String getInstanceId() {
        return instanceId;
    }

    public ScreenInjector getScreenInjector() {
        return screenInjector;
    }
}
