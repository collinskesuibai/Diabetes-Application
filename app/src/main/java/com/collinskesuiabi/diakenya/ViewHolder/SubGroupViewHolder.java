package com.collinskesuiabi.diakenya.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.R;


public class SubGroupViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener
{
    public TextView txtTitle;
    public ImageView imageSubGroup;

    private ItemClickListener itemClickListener;


    public SubGroupViewHolder(View itemView) {
        super(itemView);
       txtTitle = itemView.findViewById(R.id.txtSubGroupItem);
       imageSubGroup= itemView.findViewById(R.id.recyclerViewImageSubGroup);




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
        menu.setHeaderTitle("Select The Action");


    }
}

