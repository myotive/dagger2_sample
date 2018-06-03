package com.pluralsight.dagger2example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.pluralsight.dagger2example.adapter.RepositoryContentAdapter;
import com.pluralsight.dagger2example.di.RepositoryDetailComponent;
import com.pluralsight.dagger2example.di.modules.RepositoryDetailModule;
import com.pluralsight.dagger2example.mvp.repositoryContent.RepositoryContentContract;
import com.pluralsight.dagger2example.network.models.Content;
import com.pluralsight.dagger2example.network.models.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

public class RepositoryDetailActivity extends BaseActivity
        implements RepositoryContentContract.RepositoryContentView {

    private static final String REPOSITORY_BUNDLE_KEY = "REPOSITORY_BUNDLE_KEY";
    public static Intent getIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryDetailActivity.class);
        intent.putExtra(REPOSITORY_BUNDLE_KEY, repository);
        return intent;
    }

    @Inject
    RepositoryContentContract.RepositoryContentPresenter presenter;

    // Intent Data
    private Repository repository;

    // Local Data
    private Stack<String> history = new Stack<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RepositoryContentAdapter repositoryContentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        // Setup actionbar back buttons.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null) {
            repository = intent.getParcelableExtra(REPOSITORY_BUNDLE_KEY);
        } else {
            finish();
        }

        // Get reference to ApplicationComponent to build RepositoryDetailComponent
        // Then, use RepositoryDetailComponent to inject presenter into RepositoryDetailActivity
        SampleApplication.getApplication(this)
                .getRepositoryDetailComponent(repository.getOwner().getLogin(), repository.getName())
                .inject(this);

        setupUI();

        // Tell presenter that this activity implements our view
        presenter.takeView(this);
    }

    private void setupUI() {

        repositoryContentAdapter = new RepositoryContentAdapter(Collections.<Content>emptyList(),
                new RepositoryContentAdapter.RepositoryContentClick() {
                    @Override
                    public void OnRepositoryDetailClick(View view, Content item) {
                        onRepositoryContentItemClick(item);
                    }
                });

        RecyclerView repoContents = findViewById(R.id.rv_contents);
        repoContents.setAdapter(repositoryContentAdapter);

        swipeRefreshLayout = findViewById(R.id.str_contents);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getRepositoryContent();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.start();
    }

    @Override
    public void onBackPressed() {
        if (!history.empty()) {
            String currentItem = history.pop();
            if (currentItem.equals(repository.getName())) {
                presenter.getRepositoryContent();
            } else {
                presenter.getRepositorySubDirectory(currentItem);
            }
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void updateRepositoryList(List<Content> content) {
        repositoryContentAdapter.swapData(content);
    }

    @Override
    public void onRepositoryContentItemClick(Content item) {
        if (item.getType().equals("dir")) {

            if (history.empty()) {
                history.push(repository.getName());
            }
            else {
                history.push(item.getPath());
            }

            presenter.getRepositorySubDirectory(item.getPath());
        }
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }
}
