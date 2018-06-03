package com.pluralsight.dagger2example.di;

import com.pluralsight.dagger2example.di.modules.ActivityModule;
import com.pluralsight.dagger2example.mvp.repository.RepositoryContract;
import com.pluralsight.dagger2example.mvp.repository.RepositoryPresenter;
import com.pluralsight.dagger2example.network.GitHubAPI;

import org.mockito.Mockito;

public class MockActivityModule extends ActivityModule {
    @Override
    public RepositoryContract.RepositoryPresenter provideRepositoryPresenter(GitHubAPI gitHubAPI) {
        return Mockito.spy(new RepositoryPresenter(gitHubAPI));
    }
}
