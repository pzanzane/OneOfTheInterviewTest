package com.android.app.mobiliyatest.viewmodels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.app.mobiliyatest.R;
import com.android.app.mobiliyatest.adapters.AdapterUserRepo;
import com.android.app.mobiliyatest.models.UserRepo;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityUserSearch extends AppCompatActivity implements AdapterUserRepo.IUserRepoClicked {

    @BindView(R.id.userIcon)
    SimpleDraweeView userIcon;

    @BindView(R.id.txtUserName)
    TextView txtUserName;

    @BindView(R.id.recycler)
    RecyclerView recyclerRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getUser("octocat");
        getUserRepo("octocat");
    }

    private void getUser(String userName){

        ViewModelUserRepos viewModelUserRepos = ViewModelProviders.of(this).get(ViewModelUserRepos.class);
        viewModelUserRepos.getUser(userName).observe(this,
                (user) -> {
                    Log.d("WASTE","userName: "+user.getName() +"\n"
                            +" login: "+user.getLogin() +"\n"
                            +" avatarUrl: "+user.getAvatarUrl());

                    setUserIcon(user.getAvatarUrl());
                    setUsername(TextUtils.isEmpty(user.getName())?user.getLogin():user.getName());
                });
    }

    private void getUserRepo(String userName){

        ViewModelUserRepos viewModelUserRepos = ViewModelProviders.of(this).get(ViewModelUserRepos.class);
        viewModelUserRepos.getUserRepo(userName).observe(this,
                (list) -> {

                    for(UserRepo repo: list){
                        Log.d("WASTE","name: "+repo.getName() +"\n"
                                +" description: "+repo.getDescription() +"\n"
                                +" forks: "+repo.getForks() +"\n"
                                +" updatedAt: "+repo.getUpdatedAt() +"\n"
                                +" stargazersCount: "+repo.getStargazersCount());
                    }

                    setAdapter(list);
                });
    }

    private void bottomSheetDialog(UserRepo userRepo){

        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.SheetDialog);
        dialog.setContentView(getView(userRepo.getUpdatedAt(),userRepo.getStargazersCount(),userRepo.getForks()));
        dialog.show();
    }

    private View getView(String lastUpdated, String stars, String forks){
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_view,null);

        ((TextView)view.findViewById(R.id.txtLastUpdatedValue)).setText(lastUpdated);
        ((TextView)view.findViewById(R.id.txtStarsValue)).setText(stars);
        ((TextView)view.findViewById(R.id.txtForksValue)).setText(forks);
        return view;
    }

    private void setUserIcon(String userIconLink){

        userIcon.setImageURI(Uri.parse(userIconLink));
    }
    private void setUsername(String name){
        txtUserName.setText(name);
    }
    private void setAdapter(List<UserRepo> list){

        recyclerRepos.setLayoutManager(new LinearLayoutManager(this));
        recyclerRepos.setAdapter(new AdapterUserRepo(list,
                LayoutInflater.from(this),
                getString(R.string.text_no_description), this));
    }

    @Override
    public void onClick(UserRepo userRepo) {
        bottomSheetDialog(userRepo);
    }
}
