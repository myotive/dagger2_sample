package com.pluralsight.dagger2example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pluralsight.dagger2example.adapter.RepositoryAdapter;
import com.pluralsight.dagger2example.mvp.repository.RepositoryContract;
import com.pluralsight.dagger2example.network.models.Repository;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class RepositoryActivity extends BaseActivity
    implements RepositoryContract.RepositoryView,
        RepositoryAdapter.RepositoryItemClick{

    @Inject
    RepositoryContract.RepositoryPresenter presenter;

    private RepositoryAdapter repositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SampleApplication.getApplication(this)
                .getApplicationComponent()
                .inject(this);

        presenter.takeView(this);

        repositoryAdapter = new RepositoryAdapter(Collections.<Repository>emptyList(), this);

        RecyclerView repositoriesRecyclerView = findViewById(R.id.rv_repositories);
        repositoriesRecyclerView.setAdapter(repositoryAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.start();
    }

    @Override
    public void updateRepositoryList(List<Repository> repositories) {
        repositoryAdapter.swapData(repositories);
    }

    @Override
    public void onRepositoryItemClick(Repository item) {
        Intent intent = RepositoryDetailActivity.getIntent(this, item);
        startActivity(intent);
    }

    @Override
    public void OnRepositoryItemClick(View view, Repository item) {
        onRepositoryItemClick(item);
    }
}
