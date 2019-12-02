package com.android.app.mobiliyatest.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("login")
    private String login;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }
}
