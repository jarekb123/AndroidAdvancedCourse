package pl.butajlo.androidadvanced.details

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.Types
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

import pl.butajlo.androidadvanced.data.RepoRepository
import pl.butajlo.androidadvanced.models.Contributor
import pl.butajlo.androidadvanced.models.Repo
import pl.butajlo.androidadvanced.testutils.TestUtils
import java.io.IOException

class RepoDetailsPresenterTest {

    companion object {
        const val OWNER = "owner"
        const val NAME = "name"
    }

    private val repo = TestUtils.loadJson("mock/get_repo.json", Repo::class.java)
    private val contributors = TestUtils.loadJson<List<Contributor>>("mock/get_contributors.json",
            Types.newParameterizedType(List::class.java, Contributor::class.java))

    val repoRepository = mock<RepoRepository> {
        on(it.getRepo(OWNER, NAME)).doReturn(Single.just(repo))
        on(it.getContributors(repo.contributorsUrl)).doReturn(Single.just(contributors))
    }
    val repoConsumer = mock<Consumer<Repo>>()
    val contributorConsumer = mock<Consumer<List<Contributor>>>()
    val detailErrorConsumer = mock<Consumer<Throwable>>()
    val contributorErrorConsumer = mock<Consumer<Throwable>>()

    val viewModel = mock<RepoDetailsViewModel> {
        on(it.processRepo()).doReturn(repoConsumer)
        on(it.processContributors()).doReturn(contributorConsumer)
        on(it.detailsError()).doReturn(detailErrorConsumer)
        on(it.contributorsError()).doReturn(contributorErrorConsumer)
    }


    @Test
    fun repoDetails() {
        initPresenter()
        verify(repoConsumer).accept(repo)
    }

    @Test
    fun repoDetailsError() {
        val throwable = IOException()
        whenever(repoRepository.getRepo(OWNER, NAME)).doReturn(Single.error(throwable))
        initPresenter()

        verify(detailErrorConsumer).accept(throwable)
    }

    @Test
    fun contributors() {
        initPresenter()
        verify(contributorConsumer).accept(contributors)
    }

    @Test
    fun contributorsError() {
        val throwable = IOException()
        whenever(repoRepository.getContributors(repo.contributorsUrl)).doReturn(Single.error(throwable))
        initPresenter()

        verify(contributorErrorConsumer).accept(throwable)
        verify(repoConsumer).accept(repo)
    }

    private fun initPresenter() {
        RepoDetailsPresenter(OWNER, NAME, repoRepository, viewModel)
    }

}