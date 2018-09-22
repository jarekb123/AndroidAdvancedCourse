package pl.butajlo.androidadvanced.test

import android.app.Activity
import android.support.test.rule.ActivityTestRule
import pl.butajlo.androidadvanced.base.TestApplication
import pl.butajlo.androidadvanced.data.RepoRepository
import pl.butajlo.androidadvanced.data.TestRepoService
import pl.butajlo.androidadvanced.ui.TestScreenNavigator

class ControllerTestRule<T : Activity>(activityClass: Class<T>)
    : ActivityTestRule<T>(activityClass, true, false) {

    val screenNavigator: TestScreenNavigator = TestApplication.getComponent().screenNavigator()
    val repoService: TestRepoService = TestApplication.getComponent().repoService()
    val repoRepository: RepoRepository = TestApplication.getComponent().repoRepository()

    fun clearState() {
        repoRepository.clearCache()
        repoService.clearErrorFlags()
        repoService.clearHoldFlags()
    }

}