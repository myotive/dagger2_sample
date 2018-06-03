package com.pluralsight.dagger2example.di.modules;

import com.pluralsight.dagger2example.di.scopes.ApplicationScope;
import com.pluralsight.dagger2example.mvp.repository.RepositoryContract;
import com.pluralsight.dagger2example.mvp.repository.RepositoryPresenter;
import com.pluralsight.dagger2example.network.GitHubAPI;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    @ApplicationScope
    public RepositoryContract.RepositoryPresenter provideRepositoryPresenter(GitHubAPI gitHubAPI){
        return new RepositoryPresenter(gitHubAPI);
    }
}
