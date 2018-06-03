package com.pluralsight.dagger2example.mvp.repository;

import com.pluralsight.dagger2example.mvp.BasePresenter;
import com.pluralsight.dagger2example.mvp.BaseView;
import com.pluralsight.dagger2example.network.models.Repository;

import java.util.List;

public interface RepositoryContract {
    interface RepositoryPresenter extends BasePresenter<RepositoryView>{
        void getRepositories(String userName);
    }

    interface RepositoryView extends BaseView<Repository>{
        void updateRepositoryList(List<Repository> repositories);
        void onRepositoryItemClick(Repository item);
    }
}
