package com.pluralsight.dagger2example.mvp;

public interface BasePresenter<T extends BaseView> {
    void start();
    void takeView(T view);
    void dropView();
}
