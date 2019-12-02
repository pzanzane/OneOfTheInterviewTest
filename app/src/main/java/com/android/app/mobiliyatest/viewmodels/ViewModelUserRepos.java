package com.android.app.mobiliyatest.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.app.mobiliyatest.remoterepo.IDeliverRemoteData;
import com.android.app.mobiliyatest.remoterepo.RemoteRepoUser;
import com.android.app.mobiliyatest.models.User;
import com.android.app.mobiliyatest.models.UserRepo;

import java.util.List;

public class ViewModelUserRepos extends ViewModel {

    public LiveData<User> getUser(String userName){

        MutableLiveData<User> mutableLiveData = new MutableLiveData<>();


        RemoteRepoUser remoteRepoUser = new RemoteRepoUser();
        remoteRepoUser.getUser(userName, new IDeliverRemoteData<User>() {
            @Override
            public void onSuccess(User user) {
                mutableLiveData.postValue(user);
            }

            @Override
            public void onFailure() {
                mutableLiveData.postValue(null);
            }
        });

        return mutableLiveData;
    }

    public LiveData<List<UserRepo>> getUserRepo(String userName){

        MutableLiveData<List<UserRepo>> mutableLiveData = new MutableLiveData<>();

        RemoteRepoUser remoteRepoUser = new RemoteRepoUser();
        remoteRepoUser.getRepos(userName, new IDeliverRemoteData<List<UserRepo>>() {
            @Override
            public void onSuccess(List<UserRepo> list) {
                mutableLiveData.postValue(list);
            }

            @Override
            public void onFailure() {
                mutableLiveData.postValue(null);
            }
        });

        return mutableLiveData;
    }
}
