package pl.butajlo.androidadvanced.trending;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bluelinelabs.conductor.Controller;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.base.TestApplication;
import pl.butajlo.androidadvanced.data.TestRepoService;
import pl.butajlo.androidadvanced.home.MainActivity;
import pl.butajlo.androidadvanced.test.ControllerTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class TredingRepoControllerTest extends ControllerTest {

    @Before
    public void setup() {
        TestApplication.getComponent().inject(this);
    }

    @Test
    public void loadRepos() {
        getRepoService().clearErrorFlags();
        launch();

        onView(withId(R.id.loading_indicator)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.tv_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.repo_list)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(allOf(withId(R.id.tv_repo_name), withText("RxJava"))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void loadReposError() {
        getRepoService().setErrorFlags(TestRepoService.FLAG_TRENDING_REPOS);
        launch();

        onView(withId(R.id.loading_indicator)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.repo_list)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.tv_error))
                .check(matches(
                        allOf(
                                withText(R.string.api_error_repos),
                                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
                        )
                ));
    }

    @NotNull
    @Override
    protected Controller controllerToLaunch() {
        return new TrendingReposController();
    }
}
