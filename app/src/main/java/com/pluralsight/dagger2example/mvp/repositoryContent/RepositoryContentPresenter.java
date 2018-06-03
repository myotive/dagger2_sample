package com.pluralsight.dagger2example.mvp.repositoryContent;

import android.util.Log;

import com.pluralsight.dagger2example.network.GitHubAPI;
import com.pluralsight.dagger2example.network.models.Content;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryContentPresenter implements RepositoryContentContract.RepositoryContentPresenter {

    private GitHubAPI gitHubAPI;
    private String repo;
    private String owner;

    private RepositoryContentContract.RepositoryContentView view;

    @Inject
    public RepositoryContentPresenter(GitHubAPI gitHubAPI, String repo, String owner)
    {
        this.gitHubAPI = gitHubAPI;
        this.repo= repo;
        this.owner = owner;
    }

    @Override
    public void getRepositoryContent() {
        gitHubAPI.GetRepoContents(owner, repo)
                .enqueue(new Callback<List<Content>>() {
                    @Override
                    public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                        if(response.isSuccessful() && response.body() != null){
                            view.updateRepositoryList(response.body());
                        }

                        view.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Content>> call, Throwable t) {
                        Log.e("RepositoryDetail", "Error calling GitHubAPI", t);
                        view.setRefreshing(false);
                    }
                });
    }

    @Override
    public void getRepositorySubDirectory(String directory) {
        gitHubAPI.GetRepoSubContents(owner, repo, directory)
                .enqueue(new Callback<List<Content>>() {
                    @Override
                    public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                        if(response.isSuccessful() && response.body() != null){
                            view.updateRepositoryList(response.body());
                        }

                        view.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Content>> call, Throwable t) {
                        Log.e("RepositoryDetail", "Error calling GitHubAPI", t);
                        view.setRefreshing(false);
                    }
                });
    }

    @Override
    public void start() {
        getRepositoryContent();
    }

    @Override
    public void takeView(RepositoryContentContract.RepositoryContentView view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
