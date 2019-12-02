package com.android.app.mobiliyatest.models;

import com.google.gson.annotations.SerializedName;

public class UserRepo {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("stargazers_count")
    private String stargazersCount;

    @SerializedName("forks")
    private String forks;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getStargazersCount() {
        return stargazersCount;
    }

    public String getForks() {
        return forks;
    }
}
