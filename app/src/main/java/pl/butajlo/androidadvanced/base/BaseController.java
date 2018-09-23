package pl.butajlo.androidadvanced.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;

import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import pl.butajlo.androidadvanced.di.Injector;
import pl.butajlo.androidadvanced.lifecycle.ScreenLifecycleTask;

public abstract class BaseController extends Controller {

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    Set<ScreenLifecycleTask> screenLifecycleTasks;

    private boolean injected = false;
    private Unbinder unbinder;

    public BaseController() {
        super();
    }

    public BaseController(Bundle bundle) {
        super(bundle);
    }

    @Override
    protected void onContextAvailable(@NonNull Context context) {
        if(!injected) {
            Injector.inject(this);
            injected = true;
        }
        super.onContextAvailable(context);
    }

    @NonNull
    @Override
    protected final View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        onViewBound(view);
        disposables.addAll(subscriptions());
        return view;
    }

    @Override
    protected void onChangeStarted(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        screenLifecycleTasks.forEach(task -> {
            if(changeType.isEnter) {
                task.onEnterScope(getView());
            } else {
                task.onExitScope();
            }
        });
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        disposables.clear();
        if(unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenLifecycleTasks.forEach(ScreenLifecycleTask::onDestroy);
    }

    protected void onViewBound(View view) {}

    protected Disposable[] subscriptions() {
        return new Disposable[0];
    }

    @LayoutRes
    protected abstract int layoutRes();
}
