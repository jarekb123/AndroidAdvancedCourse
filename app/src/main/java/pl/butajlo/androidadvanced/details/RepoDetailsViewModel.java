package pl.butajlo.androidadvanced.details;

import com.jakewharton.rxrelay2.BehaviorRelay;


import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.di.ScreenScope;
import pl.butajlo.androidadvanced.models.Contributor;
import pl.butajlo.androidadvanced.models.Repo;
import timber.log.Timber;

@ScreenScope
public class RepoDetailsViewModel {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final BehaviorRelay<RepoDetailsState> detailsStateRelay = BehaviorRelay.create();
    private final BehaviorRelay<ContributorState> contributorStateRelay = BehaviorRelay.create();

    @Inject
    RepoDetailsViewModel() {
        detailsStateRelay.accept(
                RepoDetailsState.builder().loading(true).build()
        );
        contributorStateRelay.accept(
                ContributorState.builder().loading(true).build()
        );
    }

    Observable<RepoDetailsState> repoDetails() { return detailsStateRelay; }
    Observable<ContributorState> contributors() { return contributorStateRelay; }

    Consumer<Repo> processRepo() {
        return repo -> {
            detailsStateRelay.accept(
                    RepoDetailsState.builder()
                        .loading(false)
                        .name(repo.getName())
                        .description(repo.getDescription())
                        .createdDate(repo.getCreatedDate().format(DATE_FORMATTER))
                        .updatedDate(repo.getUpdatedAt().format(DATE_FORMATTER))
                    .build()
            );
        };
    }

    Consumer<List<Contributor>> processContributors() {
        return contributors -> contributorStateRelay.accept(
                ContributorState.builder()
                    .loading(false)
                    .contributors(contributors)
                    .build()
        );
    }

    Consumer<Throwable> detailsError() {
        return throwable -> {
            Timber.e(throwable, "Error loading repo details");
            detailsStateRelay.accept(
                    RepoDetailsState.builder()
                            .loading(false)
                            .errorRes(R.string.api_error_single_repo)
                            .build()
            );
        };
    }

    Consumer<Throwable> contributorsError() {
        return throwable -> {
            Timber.e(throwable, "Error loading repo contributors");
            contributorStateRelay.accept(
                    ContributorState.builder()
                        .loading(false)
                        .errorRes(R.string.api_error_single_repo_contributors)
                        .build()
            );
        };
    }
}
