package com.rideread.rideread.common.base;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class BasePresenter<T> implements IPresenter {

    private boolean isDestroy;
    private T mView;
    private List<Subscription> mRxTasks = new ArrayList<>();

    public BasePresenter(T view) {
        this.mView = view;
    }

    public T getView() {
        return mView;
    }

    public void setView(T view) {
        this.mView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        Iterator<Subscription> iterator = mRxTasks.iterator();
        while (iterator.hasNext()) {
            Subscription subscription = iterator.next();
            if (subscription.isUnsubscribed()) {
                iterator.remove();
            }
        }
    }

    public void addRxTask(Subscription subscription) {
        mRxTasks.add(subscription);
    }

    /**
     * 需要停止Http等延迟回调请求
     */
    @Override
    public void destroy() {
        isDestroy = true;
        for (Subscription subscription : mRxTasks) {
            subscription.unsubscribe();
        }
        mRxTasks.clear();
    }

    /**
     * 保存状态
     *
     * @param outState
     */
    @Override
    public void saveInstanceState(Bundle outState) {

    }

    /**
     * 恢复状态
     *
     * @param savedInstanceState
     */
    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {

    }

    public boolean isDestroy() {
        return isDestroy;
    }

    protected void runOnUiThread(final Runnable runnable) {
        Observable.create(subscriber -> runnable.run()).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
