package pl.butajlo.androidadvanced.ui

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestScreenNavigator @Inject constructor(private val defaultScreenNavigator: DefaultScreenNavigator) : ScreenNavigator {

    /**
     * Set the Controller to launch when the Activity attaches to the ScreenNavigator. This will
     * be use instead of the Controller passed in to {ScreenNavigator#initWithRouter(Router, Controller)}
     */
    var overrideController: Controller? = null

    override fun initWithRouter(router: Router, rootScreen: Controller) {
        val launchController = overrideController ?: rootScreen
        defaultScreenNavigator.initWithRouter(router, launchController)
    }

    override fun pop() = defaultScreenNavigator.pop()

    override fun clear() = defaultScreenNavigator.clear()

    override fun goToRepoDetails(repoOwner: String, repoName: String) = defaultScreenNavigator.goToRepoDetails(repoOwner, repoName)

}