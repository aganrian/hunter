package com.example.hunter.base;


/*interface yang akan menghandle presenter dari berbagai module*/
public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}