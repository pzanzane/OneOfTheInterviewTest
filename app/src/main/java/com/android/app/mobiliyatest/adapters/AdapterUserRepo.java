package com.android.app.mobiliyatest.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.mobiliyatest.R;
import com.android.app.mobiliyatest.models.UserRepo;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterUserRepo extends RecyclerView.Adapter<AdapterUserRepo.ViewHolderRepo>
        implements View.OnClickListener {

    @Override
    public void onClick(View view) {

        int position = Integer.parseInt(String.valueOf(view.getTag()));

        if(iUserRepo!=null){
            iUserRepo.onClick(getItem(position));
        }
    }

    public interface IUserRepoClicked{
        void onClick(UserRepo userRepo);
    }

    private List<UserRepo> list = null;
    private LayoutInflater infalter = null;
    private String strNoDescription = null;
    private IUserRepoClicked iUserRepo = null;

    public AdapterUserRepo(List<UserRepo> list,
                           LayoutInflater infalter,
                           String strNoDescription,
                           IUserRepoClicked iUserRepo){
            this.list = list;
            this.infalter = infalter;
            this.strNoDescription = strNoDescription;
            this.iUserRepo = iUserRepo;
    }
    @NonNull
    @Override
    public ViewHolderRepo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = infalter.inflate(R.layout.recycle_item_repo,null);
        ViewHolderRepo holder = new ViewHolderRepo(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRepo holder, int position) {

        UserRepo repo = list.get(position);

        holder.parent.setTag(position);
        holder.parent.setOnClickListener(this);
        holder.txtRepoName.setText(repo.getName());
        holder.txtDescriptionName.setText(!TextUtils.isEmpty(repo.getDescription())?
                repo.getDescription():
                strNoDescription);
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public UserRepo getItem(int position){
        return  list!=null ? list.get(position): null;
    }
    public static class ViewHolderRepo extends RecyclerView.ViewHolder{

        @BindView(R.id.parent)
        View parent;

        @BindView(R.id.txtName)
        TextView txtRepoName;

        @BindView(R.id.txtDescription)
        TextView txtDescriptionName;

        public ViewHolderRepo(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
