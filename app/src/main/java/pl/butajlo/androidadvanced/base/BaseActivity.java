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

import java.util.UUID;

import javax.inject.Inject;

import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.di.Injector;
import pl.butajlo.androidadvanced.di.ScreenInjector;
import pl.butajlo.androidadvanced.ui.ScreenNavigator;


public abstract class BaseActivity extends AppCompatActivity
{
    private static String INSTANCE_ID_KEY = "instance_id";

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            instanceId = savedInstanceState.getString(INSTANCE_ID_KEY);
        } else {
            instanceId = UUID.randomUUID().toString();
        }
        Injector.inject(this);
        setContentView(layoutRes());

        ViewGroup screenContainer = findViewById(R.id.screen_container);
        if(screenContainer == null) {
            throw new NullPointerException("Activity must have a view with id: screen_container");
        }
        router = Conductor.attachRouter(this, screenContainer, savedInstanceState);
        screenNavigator.initWithRouter(router, initialScreen());
        monitorBackStack();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_ID_KEY, instanceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenNavigator.clear();
        if(isFinishing()) {
            Injector.clearComponent(this);
        }
    }

    @Override
    public void onBackPressed() {
        if(!screenNavigator.pop()) {
            super.onBackPressed();
        }
    }

    @LayoutRes
    protected abstract int layoutRes();

    protected abstract Controller initialScreen();

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
