package com.collinskesuiabi.diakenya.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener
{
    public TextView message;
    private CircleImageView circleImageViewsmall;

    private ItemClickListener itemClickListener;


    public MessageViewHolder(View itemView) {
        super(itemView);


        message= itemView.findViewById(R.id.txtMessage);
        circleImageViewsmall =itemView.findViewById(R.id.imageUser);





        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);



    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        try {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }
}

