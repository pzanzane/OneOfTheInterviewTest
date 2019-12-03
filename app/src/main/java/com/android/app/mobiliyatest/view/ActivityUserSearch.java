package com.android.app.mobiliyatest.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.mobiliyatest.R;
import com.android.app.mobiliyatest.adapters.AdapterUserRepo;
import com.android.app.mobiliyatest.models.UserRepo;
import com.android.app.mobiliyatest.utility.KeyboardUtil;
import com.android.app.mobiliyatest.utility.ToastUtils;
import com.android.app.mobiliyatest.utility.UtilDateFormat;
import com.android.app.mobiliyatest.viewmodels.ViewModelUserRepos;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityUserSearch extends AppCompatActivity implements AdapterUserRepo.IUserRepoClicked {

    @BindView(R.id.userIcon)
    SimpleDraweeView userIcon;

    @BindView(R.id.txtUserName)
    TextView txtUserName;

    @BindView(R.id.recycler)
    RecyclerView recyclerRepos;

    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.btnSearch)
    Button btnSearch;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.noUserFoundError)
    TextView noUserFoundError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userIcon.setTag(userIcon.getY());
        txtUserName.setTag(txtUserName.getY());
    }

    private void getUser(String userName){

        ViewModelUserRepos viewModelUserRepos = ViewModelProviders.of(this).get(ViewModelUserRepos.class);
        viewModelUserRepos.getUser(userName).observe(this,
                (user) -> {

                    hideProgress();

                    if(user == null){
                        ToastUtils.showToast(getString(R.string.search_no_user_found_message));
                        noUserFoundError.setText((getString(R.string.no_user_found_error)+": "+userName));
                        return;
                    }
                    setUsername(TextUtils.isEmpty(user.getName())?user.getLogin():user.getName());
                    setUserIcon(user.getAvatarUrl());
                    getUserRepo(userName);
                });
    }

    private void getUserRepo(String userName){

        ViewModelUserRepos viewModelUserRepos = ViewModelProviders.of(this).get(ViewModelUserRepos.class);
        viewModelUserRepos.getUserRepo(userName).observe(this,
                (list) -> {

                    if(list == null || list.size() <= 0){
                        ToastUtils.showToast(getString(R.string.search_no_repository_found_message));
                        return;
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

        String formatedDate = null;
        try {
            formatedDate = UtilDateFormat.format(UtilDateFormat.yyyy_MM_dd_T_HH_mm_ss,
                    UtilDateFormat.MMM_dd_yyyy_h_mm_ss_a, lastUpdated);
        } catch (ParseException e) {
            e.printStackTrace();

            formatedDate = lastUpdated;
        }

        ((TextView)view.findViewById(R.id.txtLastUpdatedValue)).setText(formatedDate);
        ((TextView)view.findViewById(R.id.txtStarsValue)).setText(stars);
        ((TextView)view.findViewById(R.id.txtForksValue)).setText(forks);
        return view;
    }

    private void setUserIcon(String userIconLink){
        userIcon.setImageURI(Uri.parse(userIconLink));
        animate(userIcon, 300);
    }
    private void setUsername(String name){
        txtUserName.setText(name);
        animate(txtUserName, 200);
    }
    private void setAdapter(List<UserRepo> list){


        recyclerRepos.setLayoutManager(new LinearLayoutManager(this));
        setLayoutAnimation();
        recyclerRepos.setAdapter(new AdapterUserRepo(list,
                LayoutInflater.from(this),
                getString(R.string.text_no_description), this));
    }

    @Override
    public void onClick(UserRepo userRepo) {
        bottomSheetDialog(userRepo);
    }

    @OnClick(R.id.btnSearch)
    public void searchClicked(View view){

        String textSearch = edtSearch.getText().toString();

        if(TextUtils.isEmpty(textSearch.trim())){
            ToastUtils.showToast(getString(R.string.search_empty_message));
           return;
        }


        setAlpha(txtUserName, 0);
        setViewPosition(Float.parseFloat(String.valueOf(txtUserName.getTag())), txtUserName);

        setAlpha(userIcon, 0);
        setViewPosition(Float.parseFloat(String.valueOf(userIcon.getTag())), userIcon);

        recyclerRepos.setAdapter(null);

        showProgress();
        KeyboardUtil.hideSoftKeyboard(this, edtSearch);
        getUser(textSearch);
    }

    private void animate(View view, long delay){

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0.f, 0.5f, 1.0f).setDuration(1000),
                ObjectAnimator.ofFloat(view, "y", view.getY()-30).setDuration(1200));
        animatorSet.setStartDelay(delay);
        animatorSet.start();
    }

    private void setViewPosition(float y, View view){

        view.setTranslationY(y);
    }
    private void setAlpha(View view, float alpha){
        view.setAlpha(alpha);
    }
    private void setLayoutAnimation(){
        int resId = R.anim.layout_animation_recycler;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerRepos.setLayoutAnimation(animation);
    }
    private void showProgress(){
        progressbar.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progressbar.setVisibility(View.GONE);
    }
}
