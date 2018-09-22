package pl.butajlo.androidadvanced.test

import android.content.Intent
import com.bluelinelabs.conductor.Controller
import org.junit.Rule
import pl.butajlo.androidadvanced.home.MainActivity

abstract class ControllerTest {

    @Rule
    @JvmField
    val testRule = ControllerTestRule(MainActivity::class.java)

    protected val repoService = testRule.repoService
    protected val repoRepository = testRule.repoRepository
    protected val screenNavigator = testRule.screenNavigator

    init {
        screenNavigator.overrideController = ctrlToLaunch()
    }

    private fun ctrlToLaunch() : Controller {
        return controllerToLaunch()
    }

    protected abstract fun controllerToLaunch(): Controller

    protected fun launch() {
        launch(null)
    }

    protected fun launch(intent: Intent?) {
        testRule.launchActivity(intent)
    }
}