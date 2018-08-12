package pl.butajlo.androidadvanced.data;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TestRepoServiceModule {

    @Binds
    abstract RepoService bindService(TestRepoService repoService);
}
