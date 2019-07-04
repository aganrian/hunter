package com.example.hunter.base;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}