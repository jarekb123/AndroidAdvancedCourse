package pl.butajlo.androidadvanced.details;

import com.squareup.moshi.Types;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.models.Contributor;
import pl.butajlo.androidadvanced.models.Repo;
import pl.butajlo.androidadvanced.testutils.TestUtils;

public class RepoDetailsViewModelTest {

    private RepoDetailsViewModel viewModel;

    private Repo repo = TestUtils.loadJson("mock/repos/get_repo.json", Repo.class);
    private List<Contributor> contributors = TestUtils.loadJson("mock/repos/contributors/get_contributors.json",
            Types.newParameterizedType(List.class, Contributor.class));

    @Before
    public void setUp() throws Exception {
        viewModel = new RepoDetailsViewModel();
    }

    @Test
    public void repoDetails() throws Exception {
        viewModel.processRepo().accept(repo);

        viewModel.repoDetails().test().assertValue(
                new RepoDetailsState(
                        false,
                        "RxJava",
                        "RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.",
                        "08.01.2013",
                        "06.10.2017"
                )
        );
    }

    @Test
    public void detailsError() throws Exception {
        viewModel.detailsError().accept(new IOException());

        viewModel.repoDetails().test().assertValue(
                new RepoDetailsState(false,null,null,null,null, R.string.api_error_single_repo)
        );
    }

    @Test
    public void contributors() throws Exception {
        viewModel.processContributors().accept(contributors);

        viewModel.contributors().test().assertValue(
                new ContributorState(false, contributors)
        );
    }

    @Test
    public void contributorsError() throws Exception {
        viewModel.contributorsError().accept(new IOException());
        viewModel.contributors().test().assertValue(
                new ContributorState(false, null, R.string.api_error_single_repo_contributors)
        );
    }

}