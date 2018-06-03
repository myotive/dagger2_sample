package com.pluralsight.dagger2example.mvp.repositoryContent;

import com.pluralsight.dagger2example.mvp.BasePresenter;
import com.pluralsight.dagger2example.mvp.BaseView;
import com.pluralsight.dagger2example.network.models.Content;
import com.pluralsight.dagger2example.network.models.Repository;

import java.util.List;

public interface RepositoryContentContract {
    interface RepositoryContentPresenter extends BasePresenter<RepositoryContentView>{
        void getRepositoryContent();
        void getRepositorySubDirectory(String directory);
    }

    interface RepositoryContentView extends BaseView<Content>{
        void updateRepositoryList(List<Content> content);
        void onRepositoryContentItemClick(Content item);

        void setRefreshing(boolean isRefreshing);
    }
}
