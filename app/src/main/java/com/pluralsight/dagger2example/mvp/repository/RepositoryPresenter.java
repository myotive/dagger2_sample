package com.pluralsight.dagger2example.mvp.repository;

import android.support.annotation.Nullable;
import android.util.Log;

import com.pluralsight.dagger2example.network.GitHubAPI;
import com.pluralsight.dagger2example.network.models.Repository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryPresenter
        implements RepositoryContract.RepositoryPresenter {

    private GitHubAPI gitHubAPI;
    @Nullable
    private RepositoryContract.RepositoryView repositoryView;

    @Inject
    public RepositoryPresenter(GitHubAPI gitHubAPI){
        this.gitHubAPI = gitHubAPI;
    }

    @Override
    public void getRepositories(String userName) {
        gitHubAPI.GetRepos(userName)
                .enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                        if(repositoryView != null && response.isSuccessful()){
                            repositoryView.updateRepositoryList(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call, Throwable t) {
                        Log.e("RepositoryPresenter", "Error Calling API", t);
                    }
                });
    }

    @Override
    public void start() {

        // todo: we should get this username from somewhere
        this.getRepositories("myotive");
    }

    @Override
    public void takeView(RepositoryContract.RepositoryView view) {
        this.repositoryView = view;
    }

    @Override
    public void dropView() {
        this.repositoryView = null;
    }
}
