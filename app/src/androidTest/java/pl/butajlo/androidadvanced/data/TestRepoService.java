package pl.butajlo.androidadvanced.data;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

import pl.butajlo.androidadvanced.test.TestUtils;

@Singleton
public class TestRepoService implements RepoService {


    private final TestUtils testUtils;
    private boolean sendError;

    @Inject
    TestRepoService(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    @Override
    public Single<TrendingReposResponse> getTrendingRepos() {
        if(!sendError) {
            TrendingReposResponse reposResponse = testUtils.loadJson("mock/get_trending_repos.json", TrendingReposResponse.class);
            return Single.just(reposResponse);
        } else {
            return Single.error(new IOException());
        }
    }

    public void setSendError(boolean sendError) {
        this.sendError = sendError;
    }
}
