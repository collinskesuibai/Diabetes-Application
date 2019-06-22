package com.collinskesuiabi.diakenya;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.ViewHolder.ChatUsersAdapter;
import com.collinskesuiabi.diakenya.ViewHolder.ChatViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class Fragment_doctor extends Fragment {
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference userList,user_chat;

    RecyclerView recyclerView;
    String phone, department;

    StringBuilder date;

    String todaysDay;
    ProgressBar progressBar;
    ArrayList<DiaUser> mUsers = new ArrayList<DiaUser>();

    FloatingActionButton floatingActionButton;
    final int WRITE_REQUEST_CODE = 1045;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS


    };
    FirebaseRecyclerAdapter<DiaUser,ChatViewHolder> adapter;
    ChatUsersAdapter chatUsersAdapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        Paper.init(getContext());
        database = FirebaseDatabase.getInstance();
        recyclerView = view.findViewById(R.id.recyclerViewUsers);
        recyclerView.setHasFixedSize(true);
        user_chat = database.getReference("User");

        progressBar = view.findViewById(R.id.progressbarchat);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loadUserCharts2();
//
         if(Common.currentUser != null){
             loadUserCharts2();

        }else{
             getCurrentUser();

         }
        return view;
    }


    public void getCurrentUser(){
        if(!hasPermissions(getContext(), PERMISSIONS)){
            ActivityCompat.requestPermissions((Activity) getContext(), PERMISSIONS, PERMISSION_ALL);
        }else {
            try {
                if(Common.isConnectedInternet(getActivity().getBaseContext()) ){

                    phone = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference table_user = database.getReference("User");






                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            DiaUser user = dataSnapshot.child(phone).getValue(DiaUser.class);
                            user.setPhone(phone);

                            Common.currentUser = user;

                            user = dataSnapshot.child(Common.currentUser.getPhone()).getValue(DiaUser.class);

                            assert user != null;


                            if (Common.Phone_Text != null){
                                //  loadUserChat();

                            }else {
                                assert user != null;
                                Paper.book().write(Common.USER_KEY,Common.currentUser.getPhone());
//                                Paper.book().write(Common.UNI_KEY,Common.currentUser.getUniversity());
//                                Paper.book().write(Common.USER_DEPART,Common.currentUser.getScheduale());




                            }




                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Error with database", Toast.LENGTH_SHORT).show();

                        }
                    });


                }else {
                    Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {

                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();


                e.printStackTrace();
            }



        }




        //  setupDrawer();




    }
    private void loadUserCharts2() {

        user_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phone = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    DiaUser userChart = snapshot.getValue(DiaUser.class);
                    if (userChart.getPhone().equals(phone)){

                    }else{
                        mUsers.add(userChart);

                    }





                }

                DiaUser user = dataSnapshot.child(phone).getValue(DiaUser.class);
                user.setPhone(phone);

                Common.currentUser = user;

                user = dataSnapshot.child(Common.currentUser.getPhone()).getValue(DiaUser.class);
                if (Common.Phone_Text != null){


                }else {
                    assert user != null;

                    Paper.book().write(Common.USER_KEY,Common.currentUser.getPhone());



                }


                progressBar.setVisibility(View.INVISIBLE);
                chatUsersAdapter = new ChatUsersAdapter(getContext(), mUsers);
                recyclerView.setAdapter(chatUsersAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUsers(){
        Query searchByName = null;
        try {
            searchByName = user_chat.orderByChild("university").equalTo(Common.currentUser.getPerson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FirebaseRecyclerOptions<DiaUser> options =new FirebaseRecyclerOptions.Builder<DiaUser>()
                .setQuery(user_chat,DiaUser.class)
                .build();
        adapter= new FirebaseRecyclerAdapter<DiaUser, ChatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull final DiaUser model) {



                holder.Lastmessage.setText(model.getId());
                holder.namesOfPerson.setText(model.getName());
                Picasso.with(getContext()).load(model.getImage()).into(holder.circleImageView);

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(getContext() ,More.class);
                        intent.putExtra("phone",model.getPhone());
                        intent.putExtra("name",model.getName());
                        intent.putExtra("image",model.getImage());
                        startActivity(intent);
                    }
                });


                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View layout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_item ,parent , false);
                return  new ChatViewHolder(layout);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)


                    return;
                }

            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onStop() {

        super.onStop();

    }

    @Override
    public void onResume() {

        super.onResume();

    }

    @Override
    public void onStart() {

        super.onStart();

    }

  
}

