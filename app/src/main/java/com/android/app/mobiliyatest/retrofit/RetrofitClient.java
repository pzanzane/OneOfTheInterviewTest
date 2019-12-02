package com.android.app.mobiliyatest.retrofit;

import com.android.app.mobiliyatest.models.User;
import com.android.app.mobiliyatest.models.UserRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitClient {

    @GET("/users/{userName}")
    Call<User> getUserProfile(@Path("userName") String userName);

    @GET("/users/{userName}/repos")
    Call<List<UserRepo>> getRepoForUser(@Path("userName") String userName);

}
