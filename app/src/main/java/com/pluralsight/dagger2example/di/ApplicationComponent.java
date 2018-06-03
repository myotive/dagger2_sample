package com.pluralsight.dagger2example.di;

import com.pluralsight.dagger2example.RepositoryActivity;
import com.pluralsight.dagger2example.di.modules.ActivityModule;
import com.pluralsight.dagger2example.di.modules.NetworkModule;
import com.pluralsight.dagger2example.di.modules.RepositoryDetailModule;
import com.pluralsight.dagger2example.di.scopes.ApplicationScope;
import com.pluralsight.dagger2example.mvp.repository.RepositoryContract;
import com.pluralsight.dagger2example.network.GitHubAPI;

import dagger.Component;
import okhttp3.OkHttpClient;

@Component(modules = {NetworkModule.class, ActivityModule.class})
@ApplicationScope
public interface ApplicationComponent {

    void inject(RepositoryActivity activity);

    RepositoryDetailComponent plus(RepositoryDetailModule repositoryDetailModule);

    GitHubAPI githubApi();
    OkHttpClient okhttp();
    RepositoryContract.RepositoryPresenter repositoryPresenter();
}
