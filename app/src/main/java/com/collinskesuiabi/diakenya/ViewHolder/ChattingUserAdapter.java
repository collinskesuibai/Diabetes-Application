package com.collinskesuiabi.diakenya.ViewHolder;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Inbox_Activity;
import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.Model.DataChatMessages;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.More;
import com.collinskesuiabi.diakenya.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChattingUserAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private Context context;
    private List<DataChatMessages> UserList;
    FirebaseDatabase database;
    DatabaseReference userList,user_chat;

    public ChattingUserAdapter(Context context, List<DataChatMessages> userList) {
        this.context = context;
        UserList = userList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.chat_items,parent,false);
            database = FirebaseDatabase.getInstance();
        user_chat = database.getReference("User");
            return new ChatViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder viewHolder, final int position) {
       // Toast.makeText(context, UserList.get(position).getName(), Toast.LENGTH_SHORT).show();


        viewHolder.Lastmessage.setText(UserList.get(position).getMessage());
        viewHolder.messageDate.setText(UserList.get(position).getDate());
        user_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                   DiaUser userChart = snapshot.getValue(DiaUser.class);

                    if (userChart.getPhone().equals(UserList.get(position).getMessageId())){

                      //  viewHolder.messageDate.setText( Common.getDate(Long.parseLong(snapshot.getKey())));
                          Picasso.with(viewHolder.circleImageView.getContext()).load(userChart.getImage()).into(viewHolder.circleImageView);
                          viewHolder.namesOfPerson.setText(userChart.getName());

                    }else{


                    }



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        final DataChatMessages local= UserList.get(position);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(viewHolder.circleImageView.getContext() ,Inbox_Activity.class);
                if (UserList.get(position).getSender().equals(Common.Phone_Text)){
                    intent.putExtra("phone",UserList.get(position).getReceiver());
                }else {
                    intent.putExtra("phone",UserList.get(position).getSender());

                }

                intent.putExtra("name",viewHolder.namesOfPerson.getText().toString());
              //  intent.putExtra("image",viewHolder.circleImageView.getAutofillValue());
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
    public void restoreItem (   DataChatMessages item,int position){
           UserList.add(position,item);
        notifyItemInserted(position);
    }
    public DataChatMessages getItem(int position){
        return UserList.get(position);
    }
}

