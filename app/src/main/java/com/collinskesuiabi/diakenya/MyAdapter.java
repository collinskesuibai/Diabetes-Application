package com.collinskesuiabi.diakenya;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset,mImages,mDescription;
    private MyAdapter context =MyAdapter.this;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view,parent,false);
        return new MyViewHolder(itemView);
    }

    public MyAdapter(String[] mDataset) {
        this.mDataset = mDataset;


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView,othername;


        public MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView1);

        }
    }
}
