package com.collinskesuiabi.diakenya.ViewHolder;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.collinskesuiabi.diakenya.Inbox_Activity;
import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.More;
import com.collinskesuiabi.diakenya.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatViewHolder> {



    private Context context;
    private List<DiaUser> UserList;

    public ChatUsersAdapter(Context context, List<DiaUser> userList) {
        this.context = context;
        UserList = userList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.chat_item,parent,false);
            return new ChatViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder viewHolder, final int position) {

        viewHolder.namesOfPerson.setText(UserList.get(position).getName());

        Picasso.with(viewHolder.circleImageView.getContext()).load(UserList.get(position).getImage()).into(viewHolder.circleImageView);


        final DiaUser local= UserList.get(position);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {


                Intent intent = new Intent(viewHolder.circleImageView.getContext() ,Inbox_Activity.class);
                intent.putExtra("phone",UserList.get(position).getPhone());
                intent.putExtra("name",UserList.get(position).getName());
                intent.putExtra("image",UserList.get(position).getImage());
                viewHolder.circleImageView.getContext().startActivity(intent);


            }
        });

    }




    @Override
    public int getItemCount() {
            return UserList.size();
    }
    public void removeItemint (int position){
        UserList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem (   DiaUser item,int position){
           UserList.add(position,item);
        notifyItemInserted(position);
    }
    public DiaUser getItem(int position){
        return UserList.get(position);
    }
}

