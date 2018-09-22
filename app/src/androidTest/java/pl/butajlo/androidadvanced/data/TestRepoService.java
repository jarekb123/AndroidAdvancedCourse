package pl.butajlo.androidadvanced.data;

import android.os.Handler;
import android.os.Looper;

import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import pl.butajlo.androidadvanced.models.Contributor;
import pl.butajlo.androidadvanced.models.Repo;
import pl.butajlo.androidadvanced.test.TestUtils;

@Singleton
public class TestRepoService implements RepoService {

    public static final int FLAG_TRENDING_REPOS = 1;
    public static final int FLAG_GET_REPO = 2;
    public static final int FLAG_GET_CONTRIBUTORES = 3;


    private final TestUtils testUtils;

    private int errorFlags;
    private int holdFlags;

    @Inject
    TestRepoService(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    @Override
    public Single<TrendingReposResponse> getTrendingRepos() {
        if ((errorFlags & FLAG_TRENDING_REPOS) == 0) {
            TrendingReposResponse reposResponse = testUtils.loadJson("mock/get_trending_repos.json", TrendingReposResponse.class);
            if ((holdFlags & FLAG_TRENDING_REPOS) == FLAG_TRENDING_REPOS) {
                return holdingSingle(reposResponse, FLAG_TRENDING_REPOS);
            }
            return Single.just(reposResponse);
        } else {
            return Single.error(new IOException());
        }
    }

    @Override
    public Single<Repo> getRepo(String repoOwner, String repoName) {
        if ((errorFlags & FLAG_GET_REPO) == 0) {
            Repo repo = testUtils.loadJson("mock/get_repo.json", Repo.class);
            if ((holdFlags & FLAG_GET_REPO) == FLAG_GET_REPO) {
                return holdingSingle(repo, FLAG_GET_REPO);
            }
            return Single.just(repo);

        }
        return Single.error(new IOException());
    }

    @Override
    public Single<List<Contributor>> getContributors(String url) {
        if((errorFlags & FLAG_GET_CONTRIBUTORES) == 0) {
            List<Contributor> contributors = testUtils.loadJson("mock/get_contributors.json", Types.newParameterizedType(List.class, Contributor.class));
            if((holdFlags & FLAG_GET_CONTRIBUTORES) == FLAG_GET_CONTRIBUTORES) {
                return holdingSingle(contributors, holdFlags);
            }
            return Single.just(contributors);
        }
        return Single.error(new IOException());
    }

    public void setErrorFlags(int errorFlags) {
        this.errorFlags = errorFlags;
    }

    public void clearErrorFlags() {
        this.errorFlags = 0;
    }

    public void setHoldFlags(int holdFlags) {
        this.holdFlags = holdFlags;
    }

    public void clearHoldFlags() {
        this.holdFlags = 0;
    }

    private <T> Single<T> holdingSingle(T result, int flag) {
        return Single.create(emitter -> {
            final Handler handler = new Handler(Looper.getMainLooper());
            Runnable holdRunnable = new Runnable() {
                @Override
                public void run() {
                    if ((holdFlags & flag) == flag) {
                        handler.postDelayed(this, 50);
                    } else {
                        emitter.onSuccess(result);
                    }
                }
            };
            holdRunnable.run();
        });
    }
}
