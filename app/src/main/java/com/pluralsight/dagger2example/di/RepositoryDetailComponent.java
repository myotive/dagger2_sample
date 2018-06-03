package com.pluralsight.dagger2example.di;

import com.pluralsight.dagger2example.RepositoryDetailActivity;
import com.pluralsight.dagger2example.di.modules.RepositoryDetailModule;
import com.pluralsight.dagger2example.di.scopes.RepositoryDetailScope;
import com.pluralsight.dagger2example.mvp.repositoryContent.RepositoryContentContract;

import dagger.Subcomponent;

@RepositoryDetailScope
@Subcomponent(modules = {RepositoryDetailModule.class})
public interface RepositoryDetailComponent {
    //void inject(RepositoryContentFragment fragment);

    void inject(RepositoryDetailActivity activity);

    RepositoryContentContract.RepositoryContentPresenter repositoryContentPresenter();
}
