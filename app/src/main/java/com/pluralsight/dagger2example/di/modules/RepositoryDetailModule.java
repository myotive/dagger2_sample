package com.pluralsight.dagger2example.di.modules;

import com.pluralsight.dagger2example.di.scopes.RepositoryDetailScope;
import com.pluralsight.dagger2example.mvp.repositoryContent.RepositoryContentContract;
import com.pluralsight.dagger2example.mvp.repositoryContent.RepositoryContentPresenter;
import com.pluralsight.dagger2example.network.GitHubAPI;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryDetailModule {

    protected String owner;
    protected String repo;

    public RepositoryDetailModule(String owner, String repo){
        this.owner =  owner;
        this.repo = repo;
    }

    @RepositoryDetailScope
    @Provides
    public RepositoryContentContract.RepositoryContentPresenter provideRepositoryContentPresenter(GitHubAPI gitHubAPI){
        return new RepositoryContentPresenter(gitHubAPI, repo, owner);
    }
}
