package com.android.app.mobiliyatest.remoterepo;

public interface IDeliverRemoteData<T> {

    void onSuccess(T t);
    void onFailure();
}
