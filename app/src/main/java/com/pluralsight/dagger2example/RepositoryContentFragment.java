package com.pluralsight.dagger2example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pluralsight.dagger2example.adapter.RepositoryContentAdapter;
import com.pluralsight.dagger2example.mvp.repositoryContent.RepositoryContentContract;
import com.pluralsight.dagger2example.network.models.Content;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

public class RepositoryContentFragment extends Fragment
    implements RepositoryContentContract.RepositoryContentView
{
    public static RepositoryContentFragment newInstance(){
        return new RepositoryContentFragment();
    }

    @Inject
    RepositoryContentContract.RepositoryContentPresenter presenter;

    private Stack<String> history = new Stack<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RepositoryContentAdapter repositoryContentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((RepositoryDetailActivity) getActivity())
                .getRepositoryDetailComponent()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_content, container, false);

        repositoryContentAdapter = new RepositoryContentAdapter(Collections.<Content>emptyList(),
                new RepositoryContentAdapter.RepositoryContentClick() {
            @Override
            public void OnRepositoryDetailClick(View view, Content item) {
                onRepositoryContentItemClick(item);
            }
        });

        RecyclerView repoContents = view.findViewById(R.id.rv_contents);
        repoContents.setAdapter(repositoryContentAdapter);


        swipeRefreshLayout = view.findViewById(R.id.str_contents);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getRepositoryContent();
            }
        });

        presenter.takeView(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.start();
    }

    @Override
    public void updateRepositoryList(List<Content> content) {
        repositoryContentAdapter.swapData(content);
    }

    @Override
    public void onRepositoryContentItemClick(Content item) {
        if(item.getType().equals("dir")){
            history.push(item.getPath());
            presenter.getRepositorySubDirectory(item.getPath());
        }
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            history.pop();
            String currentItem = history.peek();
            if(currentItem != null && !currentItem.equals("")){
                presenter.getRepositorySubDirectory(currentItem);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
