package com.android.app.mobiliyatest.remoterepo;

import com.android.app.mobiliyatest.models.User;
import com.android.app.mobiliyatest.models.UserRepo;
import com.android.app.mobiliyatest.retrofit.RestClient;
import com.android.app.mobiliyatest.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteRepoUser {


    public void getUser(String userName, IDeliverRemoteData<User> remoteData){



        RetrofitClient retrofitClient = RestClient.createService(RetrofitClient.class);
        Call<User> call = retrofitClient.getUserProfile(userName);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    User user = response.body();

                    remoteData.onSuccess(user);

                }else{
                    //We should create custom ErrorConverter,
                    // which will be responsible to parse Custom Error Object from ErrorBody
                    //For sake of simplicity, I am considering no value for now.
                    remoteData.onFailure();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                remoteData.onFailure();
            }
        });

    }

    public void getRepos(String userName, IDeliverRemoteData<List<UserRepo>> remoteData){

        RetrofitClient retrofitClient = RestClient.createService(RetrofitClient.class);
        Call<List<UserRepo>> call = retrofitClient.getRepoForUser(userName);
        call.enqueue(new Callback<List<UserRepo>>() {
            @Override
            public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {

                if(response.isSuccessful()){
                    List<UserRepo> list = response.body();

                    remoteData.onSuccess(list);

                }else{
                    //We should create custom ErrorConverter,
                    // which will be responsible to parse Custom Error Object from ErrorBody
                    //For sake of simplicity, I am considering no value for now.
                    remoteData.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<UserRepo>> call, Throwable t) {
                remoteData.onFailure();
            }
        });

    }
}
