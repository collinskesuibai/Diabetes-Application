package com.collinskesuiabi.diakenya.ViewHolder;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.Model.ChatMessages;
import com.collinskesuiabi.diakenya.Model.DiaChatMessages;
import com.collinskesuiabi.diakenya.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private Context context;
    private List<DiaChatMessages> MessageList;

    private int positionsMe = 0;
    private int positionsSender = 1;
    private int positions;


    public MessageAdapter(Context context, ArrayList<DiaChatMessages> messageList) {
        this.context = context;
        MessageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType ==positionsMe){
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_right,parent,false);
            return new MessageViewHolder(itemView);

        }else {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_left,parent,false);
            return new MessageViewHolder(itemView);

        }

    }
    @Override
    public int getItemViewType(int position) {
       if (MessageList.get(position).getSender().equals(Common.currentUser.getPhone())){
           return positionsMe;
       }else {
           return positionsSender;
       }
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder viewHolder, final int position) {
        viewHolder.message.setText(MessageList.get(position).getMessage());
    //    Picasso.with(viewHolder.message.getContext()).load(MessageList.get(position).get).into(circleImageViewsmall);
        if (MessageList.get(position).getSender().equals(Common.currentUser.getPhone())){

            positions = 0;
        }else{
            positions = 1;

        }





        final DiaChatMessages local= MessageList.get(position);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {


            }
        });

    }





    @Override
    public int getItemCount() {
        Common.sizeOfMessageList = MessageList.size();
            return MessageList.size();
    }
    public void removeItemint (int position){
        MessageList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem (   DiaChatMessages item,int position){
            MessageList.add(position,item);
        notifyItemInserted(position);
    }
    public DiaChatMessages  getItem(int position){
        return MessageList.get(position);
    }
}

