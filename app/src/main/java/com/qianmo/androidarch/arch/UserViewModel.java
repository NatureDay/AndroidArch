package com.qianmo.androidarch.arch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qianmo.androidarch.basic.Resource;
import com.qianmo.androidarch.model.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class UserViewModel extends ViewModel {

    private MutableLiveData<Resource<User>> userData = new MutableLiveData<>();

    public MutableLiveData<Resource<User>> getUserData() {
        return userData;
    }

    public void loadData() {
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        userData.postValue(new Resource<User>(Resource.Status.IDEL));
                    }

                    @Override
                    public void onNext(Long aLong) {
                        User user = new User();
                        user.setUserId("id:" + System.currentTimeMillis());
                        user.setUserName("张三:" + (int) (100 * Math.random()));
                        userData.postValue(Resource.success(user));
                    }

                    @Override
                    public void onError(Throwable t) {
                        userData.postValue(new Resource<User>(Resource.Status.ERROR, t.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
