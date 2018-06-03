package com.pluralsight.dagger2example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pluralsight.dagger2example.di.RepositoryDetailComponent;
import com.pluralsight.dagger2example.di.modules.RepositoryDetailModule;
import com.pluralsight.dagger2example.network.models.Repository;

public class RepositoryDetailActivity extends BaseActivity
{
    private RepositoryDetailComponent repositoryDetailComponent;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Intent intent = getIntent();
        if(intent != null){
            repository = intent.getParcelableExtra(REPOSITORY_BUNDLE_KEY);
        }
        else{
            finish();
        }

        repositoryDetailComponent =
                SampleApplication.getApplication(this)
                .getRepositoryDetailComponent(repository.getOwner().getLogin(), repository.getName());

        RepositoryContentFragment fragment = RepositoryContentFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public RepositoryDetailComponent getRepositoryDetailComponent() {
        return repositoryDetailComponent;
    }

    private static final String REPOSITORY_BUNDLE_KEY = "REPOSITORY_BUNDLE_KEY";
    public static Intent getIntent(Context context, Repository repository){
        Intent intent = new Intent(context, RepositoryDetailActivity.class);
        intent.putExtra(REPOSITORY_BUNDLE_KEY, repository);
        return intent;
    }
}
