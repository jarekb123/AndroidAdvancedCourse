package pl.butajlo.androidadvanced.trending;


import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.di.ScreenScope;
import pl.butajlo.androidadvanced.models.Repo;
import timber.log.Timber;

@ScreenScope
public class TrendingReposViewModel {

    private final BehaviorRelay<List<Repo>> reposRelay = BehaviorRelay.create();
    private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();
    private final BehaviorRelay<Boolean> loadingRelay = BehaviorRelay.create();

    @Inject
    TrendingReposViewModel() {

    }

    Observable<Boolean> loading() { return loadingRelay; }
    Observable<List<Repo>> repos() { return reposRelay; }
    Observable<Integer> error() { return errorRelay; }

    Consumer<Boolean> loadingUpdated() { return loadingRelay; }
    Consumer<List<Repo>> reposUpdated() {
        errorRelay.accept(-1); // refresh -> so hide errors
        return reposRelay;
    }
    Consumer<Throwable> onError() {
        return throwable -> {
            Timber.e(throwable, "Error loading repos");
            errorRelay.accept(R.string.api_error_repos);
        };
    }

}
