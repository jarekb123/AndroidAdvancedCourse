package pl.butajlo.androidadvanced.details

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.threeten.bp.format.DateTimeFormatter
import pl.butajlo.androidadvanced.R
import pl.butajlo.androidadvanced.di.ScreenScope
import pl.butajlo.androidadvanced.models.Contributor
import pl.butajlo.androidadvanced.models.Repo
import timber.log.Timber
import javax.inject.Inject

@ScreenScope
class RepoDetailsViewModel @Inject constructor() {

    companion object {
        private val DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    }

    private val detailsStateRelay = BehaviorRelay.create<RepoDetailsState>()
    private val contributorStateRelay = BehaviorRelay.create<ContributorState>()

    fun repoDetails(): Observable<RepoDetailsState> {
        return detailsStateRelay
    }

    fun contributors(): Observable<ContributorState> {
        return contributorStateRelay
    }

    fun processRepo(): Consumer<Repo> {
        return Consumer {
            detailsStateRelay.accept(
                    RepoDetailsState(false, it.name, it.description, it.createdDate.format(DATE_FORMATTER), it.updatedAt.format(DATE_FORMATTER))
            )
        }
    }

    fun processContributors(): Consumer<List<Contributor>> {
        return Consumer {
            contributorStateRelay.accept(ContributorState(false, contributors = it)            )
        }
    }

    fun detailsError(): Consumer<Throwable> {
        return Consumer {
            Timber.e(it, "Error loading repo details")
            detailsStateRelay.accept(RepoDetailsState(false, errorRes = R.string.api_error_single_repo)            )
        }
    }

    fun contributorsError(): Consumer<Throwable> {
        return Consumer {
            Timber.e(it, "Error loading repo contributors")
            contributorStateRelay.accept(
                    ContributorState(false, errorRes = R.string.api_error_single_repo_contributors)
            )
        }
    }

    init {
        detailsStateRelay.accept(RepoDetailsState(loading = true)        )
        contributorStateRelay.accept(ContributorState(true))
    }
}