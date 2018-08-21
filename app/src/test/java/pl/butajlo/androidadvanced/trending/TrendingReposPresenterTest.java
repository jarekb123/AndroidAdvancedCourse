package pl.butajlo.androidadvanced.trending;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import pl.butajlo.androidadvanced.data.RepoRepository;
import pl.butajlo.androidadvanced.data.RepoRequester;
import pl.butajlo.androidadvanced.data.TrendingReposResponse;
import pl.butajlo.androidadvanced.models.Repo;
import pl.butajlo.androidadvanced.testutils.TestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class TrendingReposPresenterTest {

    /* We want to have our tests focused on one thing we test (TrendingReposPresenter).
    * So we want to remove all other external variables. -> Mock them */

    @Mock RepoRepository repoRepository;
    @Mock TrendingReposViewModel viewModel;

    /* We need Consumer fields mocked, because we call some methods on TrendingReposViewModel
    * and we expect values from those methods (these methods return Consumer<>) */
    @Mock Consumer<Throwable> onErrorConsumer;
    @Mock Consumer<List<Repo>> onSuccessConsumer;
    @Mock Consumer<Boolean> loadingConsumer;

    private TrendingReposPresenter presenter;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this); // this will init all @Mock fields

        when(viewModel.loadingUpdated()).thenReturn(loadingConsumer);
        when(viewModel.onError()).thenReturn(onErrorConsumer);
        when(viewModel.reposUpdated()).thenReturn(onSuccessConsumer);
    }

    @Test
    public void reposLoaded() throws Exception {
        List<Repo> repos = setupSuccess(); // we setup emitting repos successfully
        initPresenter();
        // NOTE: repoRequester.getTrendingRepos() is called in loadRepos(), not in when(...)
        // (it's trivial, but these comments help me understand these things faster)

        verify(repoRepository).getTrendingRepos(); // verify if getTrendingRepos() is called on repoRequester
        verify(onSuccessConsumer).accept(repos); // verify if onSuccessConsumer accepts repos

        // verify if onErrorConsumer has no interactions (it shouldn't, because repos was loaded successfully)
        verifyZeroInteractions(onErrorConsumer);
    }

    @Test
    public void reposLoadedError() throws Exception {
        Throwable error = setupError();
        initPresenter();

        verify(onErrorConsumer).accept(error);
        verifyZeroInteractions(onSuccessConsumer);
    }

    @Test
    public void loadingSuccess() throws Exception {
        setupSuccess();
        initPresenter();

        InOrder inOrder = Mockito.inOrder(loadingConsumer);
        inOrder.verify(loadingConsumer).accept(true);
        inOrder.verify(loadingConsumer).accept(false);
    }
    @Test
    public void loadingError() throws Exception {
        //noinspection ThrowableNotThrown
        setupError();
        initPresenter();

        InOrder inOrder = Mockito.inOrder(loadingConsumer);
        inOrder.verify(loadingConsumer).accept(true);
        inOrder.verify(loadingConsumer).accept(false);
    }

    @Test
    public void onRepoClicked() throws Exception {
        //TODO
    }

    private List<Repo> setupSuccess() {
        TrendingReposResponse response = TestUtils.loadJson("mock/get_trending_repos.json", TrendingReposResponse.class);
        List<Repo> repos = response.getRepos();

        // So when the getTrendingRepos() on repoRequester is called in loadRepos()
        // we just emit repos successfully
        when(repoRepository.getTrendingRepos()).thenReturn(Single.just(repos));

        return repos;
    }

    private Throwable setupError() {
        Throwable error = new IOException();

        // So when the getTrendingRepos() on repoRequester is called in loadRepos()
        // we don't emit repos successfully, we emit error
        when(repoRepository.getTrendingRepos()).thenReturn(Single.error(error));

        return error;
    }

    /**
     * We can't just init Presenter in setup method, because there is loadRepos() called in the constructor
     */
    private void initPresenter() {
        presenter = new TrendingReposPresenter(viewModel, repoRepository);
    }
}