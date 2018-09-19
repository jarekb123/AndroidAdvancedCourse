package pl.butajlo.androidadvanced.ui

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router

interface ScreenNavigator {

    fun initWithRouter(router: Router, rootScreen: Controller)

    /**
     * Pop the actual screen
     * @return true if the screen was popped, false if not
     */
    fun pop(): Boolean

    /**
     * It is used to clean the router, if the activity is destroyed
     */
    fun clear()

    fun goToRepoDetails(repoOwner: String, repoName: String)
}