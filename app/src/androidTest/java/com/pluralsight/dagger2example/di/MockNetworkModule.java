package com.pluralsight.dagger2example.di;

import android.os.Looper;

import com.pluralsight.dagger2example.di.modules.NetworkModule;
import com.pluralsight.dagger2example.mvp.repository.RepositoryContract;
import com.pluralsight.dagger2example.network.GitHubAPI;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MockNetworkModule extends NetworkModule {

    private MockWebServer mockWebServer;

    public MockNetworkModule(MockWebServer mockWebServer) {
        this.mockWebServer = mockWebServer;
    }

/*    @Override
    public OkHttpClient provideHttpClient(HttpLoggingInterceptor logging) {

        return new OkHttpClient.Builder()
                //.retryOnConnectionFailure(true)
                .addInterceptor(logging)
                .build();
    }*/

    @Override
    public Retrofit provideRetrofit(OkHttpClient client) {

        return new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
