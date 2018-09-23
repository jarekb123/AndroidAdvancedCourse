package pl.butajlo.androidadvanced.ui

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import pl.butajlo.androidadvanced.base.BaseActivity
import pl.butajlo.androidadvanced.lifecycle.ActivityLifecycleTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestScreenNavigator @Inject constructor()
    : ActivityLifecycleTask(), ScreenNavigator {

    private val defaultScreenNavigator = DefaultScreenNavigator()

    /**
     * Set the Controller to launch when the Activity attaches to the ScreenNavigator. This will
     * be use instead of the Controller provided by {RouterProvider#initialScreen}
     */
    var overrideController: Controller? = null

    override fun onCreate(activity: BaseActivity) {
        val launchController = overrideController ?: activity.initialScreen()
        defaultScreenNavigator.initWithRouter(activity.getRouter(), launchController)
    }

    override fun onDestroy(activity: BaseActivity) {
        defaultScreenNavigator.onDestroy(activity)
    }

    override fun pop() = defaultScreenNavigator.pop()

    override fun goToRepoDetails(repoOwner: String, repoName: String) = defaultScreenNavigator.goToRepoDetails(repoOwner, repoName)

}