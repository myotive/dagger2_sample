package com.pluralsight.dagger2example.application;

import com.pluralsight.dagger2example.SampleApplication;
import com.pluralsight.dagger2example.di.ApplicationComponent;
import com.pluralsight.dagger2example.di.DaggerApplicationComponent;
import com.pluralsight.dagger2example.di.MockActivityModule;
import com.pluralsight.dagger2example.di.MockNetworkModule;
import com.pluralsight.dagger2example.di.MockRepositoryDetailModule;
import com.pluralsight.dagger2example.di.RepositoryDetailComponent;

import org.mockito.Mockito;

import okhttp3.mockwebserver.MockWebServer;

public class MockSampleApplication extends SampleApplication {

    private RepositoryDetailComponent repositoryDetailComponent;
    private MockWebServer mockWebServer;

    @Override
    public void onCreate() {
        super.onCreate();

        mockWebServer = new MockWebServer();

        ApplicationComponent applicationComponent = DaggerApplicationComponent
                .builder()
                .networkModule(new MockNetworkModule(mockWebServer))
                .activityModule(new MockActivityModule())
                .build();

        this.setComponent(applicationComponent);
    }
    
    public MockWebServer getMockWebServer() {
        return mockWebServer;
    }

    public RepositoryDetailComponent generateRepositoryDetailComponent(String owner, String repo){
        repositoryDetailComponent = getApplicationComponent()
                .plus(new MockRepositoryDetailModule(owner, repo));
        return repositoryDetailComponent;
    }

    @Override
    public RepositoryDetailComponent getRepositoryDetailComponent(String owner, String repo) {

        return repositoryDetailComponent;
    }
}
