package com.pluralsight.dagger2example;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.pluralsight.dagger2example.di.ApplicationComponent;
import com.pluralsight.dagger2example.di.DaggerApplicationComponent;
import com.pluralsight.dagger2example.di.RepositoryDetailComponent;
import com.pluralsight.dagger2example.di.modules.NetworkModule;
import com.pluralsight.dagger2example.di.modules.RepositoryDetailModule;

public class SampleApplication extends Application {

    private ApplicationComponent applicationComponent;

    /**
     * Helper method to obtain SampleApplication class from context.
     * @param context
     * @return SampleApplication
     */
    public static SampleApplication getApplication(Context context){
        return (SampleApplication)context.getApplicationContext();
    }

    /**
     * Getter method to obtain application component from Application.
     * @return ApplicationComponent
     */
    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    /**
     * Getter method to obtain Repository Detail Subcomponent
     * @param owner github login
     * @param repo github repo
     * @return RepositoryDetailComponent
     */
    public RepositoryDetailComponent getRepositoryDetailComponent(String owner, String repo){
        return applicationComponent.plus(new RepositoryDetailModule(owner, repo));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .networkModule(new NetworkModule())
                .build();
    }

    /**
     * Helper method to facilitate testing
     * @param component
     */
    @VisibleForTesting
    public void setComponent(ApplicationComponent component){
        this.applicationComponent = component;
    }
}
