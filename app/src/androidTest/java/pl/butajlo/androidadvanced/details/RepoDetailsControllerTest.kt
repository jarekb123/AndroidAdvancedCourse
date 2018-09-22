package pl.butajlo.androidadvanced.details

import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import com.bluelinelabs.conductor.Controller
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.butajlo.androidadvanced.R
import pl.butajlo.androidadvanced.data.TestRepoService
import pl.butajlo.androidadvanced.test.ControllerTest

@RunWith(AndroidJUnit4::class)
class RepoDetailsControllerTest : ControllerTest() {

    override fun controllerToLaunch(): Controller {
        return RepoDetailsController.newInstance("ReactiveX", "RxJava")
    }

    @Before
    fun setUp() {
        testRule.clearState()
    }

    @Test
    fun repoDetailsSuccess() {
        launch()
        RepoDetailsRobot.init()
                .verifyLoadingVisiblity(ViewMatchers.Visibility.GONE)
                .verifyErrorVisiblity(ViewMatchers.Visibility.GONE)
                .verifyName("RxJava")
                .verifyDescription("RxJava – Reactive Extensions for the JVM – a library for " +
                        "composing asynchronous and event-based programs using observable sequences for the Java VM.")
                .verifyCreatedDate("08.01.2013")
                .verifyUpdatedDate("06.10.2017")
    }

    @Test
    fun repoDetailsError() {
        repoService.setErrorFlags(TestRepoService.FLAG_GET_REPO)
        launch()
        RepoDetailsRobot.init()
                .verifyErrorText(R.string.api_error_single_repo)
                .verifyErrorVisiblity(ViewMatchers.Visibility.VISIBLE)
                .verifyContentVisibility(ViewMatchers.Visibility.GONE)
                .verifyLoadingVisiblity(ViewMatchers.Visibility.GONE)
    }

    @Test
    fun contributorsSuccess() {
        launch()
        RepoDetailsRobot.init()
                .verifyContributorsLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyContributorShown("benjchristensen")
                .verifyContributorsErrorVisibility(ViewMatchers.Visibility.GONE)

    }

    @Test
    fun contributorsError() {
        repoService.setErrorFlags(TestRepoService.FLAG_GET_CONTRIBUTORS)
        launch()
        RepoDetailsRobot.init()
                .verifyContributorsErrorVisibility(ViewMatchers.Visibility.VISIBLE)
                .verifyContributorsErrorText(R.string.api_error_single_repo_contributors)
    }

    @Test
    fun repoSuccessContributorsError() {
        repoService.setErrorFlags(TestRepoService.FLAG_GET_CONTRIBUTORS)
        launch()
        RepoDetailsRobot.init()
                .verifyLoadingVisiblity(ViewMatchers.Visibility.GONE)
                .verifyContributorsLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyContributorsErrorText(R.string.api_error_single_repo_contributors)
                .verifyErrorVisiblity(ViewMatchers.Visibility.GONE)
    }

    @Test
    fun loadingRepo() {
        repoService.setHoldFlags(TestRepoService.FLAG_GET_REPO)
        launch()
        RepoDetailsRobot.init()
                .verifyLoadingVisiblity(ViewMatchers.Visibility.VISIBLE)
    }

    @Test
    fun loadingContributors() {
        repoService.setHoldFlags(TestRepoService.FLAG_GET_CONTRIBUTORS)
        launch()
        RepoDetailsRobot.init()
                .verifyContributorsLoadingVisibility(ViewMatchers.Visibility.VISIBLE)
    }

}