package com.pluralsight.dagger2example.di;

import com.pluralsight.dagger2example.di.modules.RepositoryDetailModule;
import com.pluralsight.dagger2example.mvp.repositoryContent.RepositoryContentContract;
import com.pluralsight.dagger2example.mvp.repositoryContent.RepositoryContentPresenter;
import com.pluralsight.dagger2example.network.GitHubAPI;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

public class MockRepositoryDetailModule extends RepositoryDetailModule {
    public MockRepositoryDetailModule(String owner, String repo) {
        super(owner, repo);
    }

   @Override
    public RepositoryContentContract.RepositoryContentPresenter provideRepositoryContentPresenter(RepositoryContentPresenter presenter) {
        return Mockito.spy(presenter);
    }
}
