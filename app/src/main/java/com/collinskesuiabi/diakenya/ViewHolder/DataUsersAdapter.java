package com.collinskesuiabi.diakenya.ViewHolder;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collinskesuiabi.diakenya.Inbox_Activity;
import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.Model.DiaData;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DataUsersAdapter extends RecyclerView.Adapter<DataViewHolder> {



    private Context context;
    private List<DiaData> UserList;

    public DataUsersAdapter(Context context, ArrayList<DiaData> userList) {
        this.context = context;
        UserList = userList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.data_item,parent,false);
            return new DataViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder viewHolder, final int position) {

        viewHolder.namesOfPerson.setText(UserList.get(position).getValue());
        viewHolder.Lastmessage.setText(UserList.get(position).getName());

      //  Picasso.with(viewHolder.circleImageView.getContext()).load(UserList.get(position).getImage()).into(viewHolder.circleImageView);


        final DiaData local= UserList.get(position);


    }




    @Override
    public int getItemCount() {
            return UserList.size();
    }
    public void removeItemint (int position){
        UserList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem (   DiaData item,int position){
           UserList.add(position,item);
        notifyItemInserted(position);
    }
    public DiaData getItem(int position){
        return UserList.get(position);
    }
}

