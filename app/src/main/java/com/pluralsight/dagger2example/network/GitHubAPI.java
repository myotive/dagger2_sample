package com.pluralsight.dagger2example.network;

import com.pluralsight.dagger2example.network.models.Content;
import com.pluralsight.dagger2example.network.models.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubAPI {
    @GET("users/{user}/repos")
    Call<List<Repository>> GetRepos(@Path("user") String user);

    @GET("repos/{ownerName}/{repo}/contents")
    Call<List<Content>> GetRepoContents(@Path("ownerName") String ownerName, @Path("repo") String repo);

    @GET("repos/{ownerName}/{repo}/contents/{path}")
    Call<List<Content>> GetRepoSubContents(@Path("ownerName") String ownerName,
                                        @Path("repo") String repo,
                                        @Path("path") String path);
}
