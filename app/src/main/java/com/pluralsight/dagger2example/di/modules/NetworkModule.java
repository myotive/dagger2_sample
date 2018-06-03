package com.pluralsight.dagger2example.di.modules;

import com.google.gson.GsonBuilder;
import com.pluralsight.dagger2example.di.scopes.ApplicationScope;
import com.pluralsight.dagger2example.network.GitHubAPI;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    public Retrofit provideRetrofit(OkHttpClient client){

        GsonBuilder builder = new GsonBuilder(); // Set up the custom GSON converters

        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideHttpClient(HttpLoggingInterceptor logging){

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor provideInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return logging;
    }

    @Provides
    @ApplicationScope
    public GitHubAPI provideGithubAPI(Retrofit retrofit){
        return retrofit.create(GitHubAPI.class);
    }
}
