package pl.butajlo.androidadvanced.ui

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router

interface ScreenNavigator {

    /**
     * Pop the actual screen
     * @return true if the screen was popped, false if not
     */
    fun pop(): Boolean

    fun goToRepoDetails(repoOwner: String, repoName: String)
}