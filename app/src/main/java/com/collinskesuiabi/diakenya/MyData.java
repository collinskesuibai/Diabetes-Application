package com.collinskesuiabi.diakenya;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Model.DiaData;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.ViewHolder.ChatUsersAdapter;
import com.collinskesuiabi.diakenya.ViewHolder.ChatViewHolder;
import com.collinskesuiabi.diakenya.ViewHolder.DataUsersAdapter;
import com.collinskesuiabi.diakenya.ViewHolder.DataViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MyData extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference userList,user_chat;

    RecyclerView recyclerView;
    String phone, department;

    StringBuilder date;

    String todaysDay;
    ProgressBar progressBar;
    ArrayList<DiaData> mData = new ArrayList<DiaData>();
    FirebaseRecyclerAdapter<DiaData,DataViewHolder> adapter;
    DataUsersAdapter dataUsersAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);

        database = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.recyclerViewData);
        recyclerView.setHasFixedSize(true);
        user_chat = database.getReference("DiaData");

        progressBar = findViewById(R.id.progressbarchat);

        layoutManager = new LinearLayoutManager(MyData.this);
        recyclerView.setLayoutManager(layoutManager);
        loadUserCharts2();
    }

    private void loadUserCharts2() {

        user_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phone = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                mData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    DiaData userChart = snapshot.getValue(DiaData.class);
                    if (userChart.getPhone().equals(phone)){
                        mData.add(userChart);

                    }

                }


                progressBar.setVisibility(View.INVISIBLE);


                dataUsersAdapter= new DataUsersAdapter(MyData.this, mData);
                recyclerView.setAdapter(dataUsersAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
