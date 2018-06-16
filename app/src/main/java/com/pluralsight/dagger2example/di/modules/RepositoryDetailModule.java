package com.pluralsight.dagger2example.di.modules;

import com.pluralsight.dagger2example.di.qualifiers.Owner;
import com.pluralsight.dagger2example.di.qualifiers.Repository;
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

    @Provides
    @Repository
    String providesRepository(){
        return repo;
    }

    @Provides
    @Owner
    String providesOwner(){
        return owner;
    }

    @RepositoryDetailScope
    @Provides
    public RepositoryContentContract.RepositoryContentPresenter
    provideRepositoryContentPresenter(RepositoryContentPresenter repositoryContentPresenter){
        return repositoryContentPresenter;
    }
}
