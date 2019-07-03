package com.qianmo.androidarch.basic;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.subscribers.DefaultSubscriber;

public abstract class StatusSubscriber<T> extends DefaultSubscriber<T> {

    private MutableLiveData<Resource<T>> data;

    public StatusSubscriber() {
    }

    public StatusSubscriber(MutableLiveData<Resource<T>> data) {
        this.data = data;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (data != null) {
            data.postValue(new Resource<T>(Resource.Status.LOADING));
        }
    }

    @Override
    public void onNext(T t) {
        if (data != null) {
            data.postValue(new Resource<T>(Resource.Status.SUCCESS, t));
        }
    }

    @Override
    public void onError(Throwable t) {
        if (data != null) {
            data.postValue(new Resource<T>(Resource.Status.ERROR, t.getMessage()));
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 提供默认的加载信息
     *
     * @return
     */
    protected String getLoaingMessage() {
        return "加载中...";
    }

    /**
     * 提供默认的结果异常处理，可重写
     *
     * @param msg
     */
    protected void onResultError(String msg) {

    }

    /**
     * 请求成功结果的回调
     *
     * @param data
     */
    protected abstract void onResult(T data);
}
