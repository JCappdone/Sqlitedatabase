package com.example.interviewsqlite3.Adpters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.interviewsqlite3.Main2Activity;
import com.example.interviewsqlite3.Models.UserModel;
import com.example.interviewsqlite3.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shriji on 28/3/18.
 */

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.MyViewHolder> {

    private Activity mContext;
    private List<UserModel> userList;

    public UserAdpter(Activity context, List<UserModel> userList) {
        mContext = context;
        this.userList = userList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.useradpterlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final UserModel model = userList.get(position);
        holder.mTxtAuser.setText(model.getUserName());
        holder.mTxtAphone.setText(model.getUserPhone());
        holder.mImgAuser.setImageURI(Uri.parse(model.getUserImage()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Main2Activity.class);
                intent.putExtra("MODEL",model);
                mContext.startActivityForResult(intent,1234);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAuser)
        ImageView mImgAuser;
        @BindView(R.id.txtAuser)
        TextView mTxtAuser;
        @BindView(R.id.txtAphone)
        TextView mTxtAphone;
        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }
}
