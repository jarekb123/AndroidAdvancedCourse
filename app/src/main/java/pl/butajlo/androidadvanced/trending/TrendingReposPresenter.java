package pl.butajlo.androidadvanced.trending;


import javax.inject.Inject;

import pl.butajlo.androidadvanced.data.RepoRequester;
import pl.butajlo.androidadvanced.data.RepoRepository;
import pl.butajlo.androidadvanced.di.ScreenScope;
import pl.butajlo.androidadvanced.models.Repo;
import pl.butajlo.androidadvanced.ui.ScreenNavigator;

@ScreenScope
public class TrendingReposPresenter implements RepoAdapter.RepoClickedListener {

    private final TrendingReposViewModel viewModel;
    private final RepoRepository repoRepository;
    private final ScreenNavigator screenNavigator;

    @Inject
    TrendingReposPresenter(
            TrendingReposViewModel viewModel,
            RepoRepository repoRepository,
            ScreenNavigator screenNavigator
    ) {
        this.viewModel = viewModel;
        this.repoRepository = repoRepository;
        this.screenNavigator = screenNavigator;

        loadRepos();
    }

    private void loadRepos() {
        repoRepository.getTrendingRepos()
                .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
                .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
                .subscribe(viewModel.reposUpdated(), viewModel.onError());
    }

    @Override
    public void onRepoClicked(Repo repo) {
        screenNavigator.goToRepoDetails(repo.getOwner().getLogin(), repo.getName());
    }
}
