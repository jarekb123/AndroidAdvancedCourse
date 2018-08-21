package pl.butajlo.androidadvanced.trending;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import io.reactivex.observers.TestObserver;
import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.data.TrendingReposResponse;
import pl.butajlo.androidadvanced.testutils.TestUtils;

import static org.junit.Assert.*;

public class TrendingReposViewModelTest {

    private TrendingReposViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new TrendingReposViewModel();
    }

    @Test
    public void loading() throws Exception {
        TestObserver<Boolean> loadingObserver = viewModel.loading().test();
        viewModel.loadingUpdated().accept(true);
        viewModel.loadingUpdated().accept(false);

        loadingObserver.assertValues(true, false);
    }

    @Test
    public void repos() throws Exception {
        TrendingReposResponse response = TestUtils.loadJson("mock/get_trending_repos.json", TrendingReposResponse.class);
        viewModel.reposUpdated().accept(response.getRepos());

        viewModel.repos().test().assertValue(response.getRepos());
    }

    @Test
    public void error() throws Exception {
        TestObserver<Integer> errorObserver = viewModel.error().test();
        viewModel.onError().accept(new IOException());
        viewModel.reposUpdated().accept(Collections.emptyList());

        errorObserver.assertValues(R.string.api_error_repos, -1);
    }

}