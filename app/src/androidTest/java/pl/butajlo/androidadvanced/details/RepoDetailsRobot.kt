package pl.butajlo.androidadvanced.details

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.allOf
import pl.butajlo.androidadvanced.R

internal class RepoDetailsRobot {

    companion object {
        fun init(): RepoDetailsRobot = RepoDetailsRobot()
    }

    fun verifyName(name: String): RepoDetailsRobot {
        onView(withId(R.id.tv_repo_name)).check(matches(withText(name)))
        return this
    }

    fun verifyDescription(description: String): RepoDetailsRobot {
        onView(withId(R.id.tv_repo_description)).check(matches(withText(description)))
        return this
    }

    fun verifyCreatedDate(createdDate: String): RepoDetailsRobot {
        onView(withId(R.id.tv_creation_date)).check(matches(withText(createdDate)))
        return this
    }

    fun verifyUpdatedDate(updatedDate: String): RepoDetailsRobot {
        onView(withId(R.id.tv_updated_date)).check(matches(withText(updatedDate)))
        return this
    }

    fun verifyErrorText(errorRes: Int): RepoDetailsRobot {
        val textMatcher = if(errorRes == 0) { withText("") } else { withText(errorRes) }
        onView(withId(R.id.tv_error)).check(matches(textMatcher))
        return this
    }

    fun verifyErrorVisiblity(visibility: Visibility): RepoDetailsRobot {
        onView(withId(R.id.tv_error)).check(matches(withEffectiveVisibility(visibility)))
        return this
    }

    fun verifyContributorsErrorText(errorRes: Int): RepoDetailsRobot {
        val textMatcher = if(errorRes == 0) { withText("") } else { withText(errorRes) }
        onView(withId(R.id.tv_contributors_error)).check(matches(textMatcher))
        return this
    }

    fun verifyContributorsLoadingVisibility(visibility: Visibility): RepoDetailsRobot {
        onView(withId(R.id.contributor_loading_indicator)).check(matches(withEffectiveVisibility(visibility)))
        return this
    }

    fun verifyContributorShown(login: String): RepoDetailsRobot {
        onView(allOf(withId(R.id.tv_user_name), withText(login))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        return this
    }

    fun verifyContentVisibility(visibility: Visibility): RepoDetailsRobot {
        onView(withId(R.id.content)).check(matches(withEffectiveVisibility(visibility)))
        return this
    }

}