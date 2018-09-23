package pl.butajlo.androidadvanced.ui;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import pl.butajlo.androidadvanced.base.BaseActivity;
import pl.butajlo.androidadvanced.details.RepoDetailsController;
import pl.butajlo.androidadvanced.di.ActivityScope;
import pl.butajlo.androidadvanced.lifecycle.ActivityLifecycleTask;

@ActivityScope
public class DefaultScreenNavigator extends ActivityLifecycleTask implements ScreenNavigator {

    @Inject
    DefaultScreenNavigator() {}

    private Router router;

    @Override
    public void onCreate(BaseActivity activity) {
        initWithRouter(activity.getRouter(), activity.initialScreen());
    }

    @Override
    public void onDestroy(@NotNull BaseActivity activity) {
        router = null;
    }

    public void initWithRouter(Router router, Controller rootScreen) {
        this.router = router;
        if(!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(rootScreen));
        }
    }

    @Override
    public boolean pop() {
        return router != null && router.handleBack();
    }

    @Override
    public void goToRepoDetails(@NotNull String repoOwner, @NotNull String repoName) {
        if(router != null) {
            router.pushController(RouterTransaction.with(
                        RepoDetailsController.newInstance(repoOwner, repoName))
                    .pushChangeHandler(new FadeChangeHandler())
                    .popChangeHandler(new FadeChangeHandler())
            );
        }
    }
}
