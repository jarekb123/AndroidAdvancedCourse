package pl.butajlo.androidadvanced.details

import io.reactivex.functions.Consumer
import pl.butajlo.androidadvanced.data.RepoRepository
import pl.butajlo.androidadvanced.di.ScreenScope
import javax.inject.Inject
import javax.inject.Named

@ScreenScope
class RepoDetailsPresenter
    @Inject
    constructor(
            @Named("repo_owner") val repoOwner: String,
            @Named("repo_name") val repoName: String,
            val repoRepository: RepoRepository,
            val viewModel: RepoDetailsViewModel
    ) {

    init {
        repoRepository.getRepo(repoOwner, repoName)
                .doOnSuccess(viewModel.processRepo())
                .doOnError(viewModel.detailsError())
                .flatMap { repoRepository.getContributors(it.contributorsUrl)
                        .doOnError(viewModel.contributorsError())
                }
                .subscribe(viewModel.processContributors(), Consumer<Throwable?> {  })
    }
}