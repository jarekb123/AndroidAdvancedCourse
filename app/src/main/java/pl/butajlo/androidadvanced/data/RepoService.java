package pl.butajlo.androidadvanced.data;


import io.reactivex.Single;
import pl.butajlo.androidadvanced.models.Repo;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepoService {
    @GET("search/repositories?q=language:java&order=desc&sort=stars")
    Single<TrendingReposResponse> getTrendingRepos();

    @GET("repos/{owner}/{name}")
    Single<Repo> getRepo(@Path("owner") String repoOwner, @Path("name") String repoName);
}
