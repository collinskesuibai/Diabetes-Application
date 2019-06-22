package com.collinskesuiabi.diakenya;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Database.DatabaseDepart;
import com.collinskesuiabi.diakenya.Model.ChatMessages;
import com.collinskesuiabi.diakenya.Model.DataChatMessages;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.Model.DiaUserChart;


import com.collinskesuiabi.diakenya.ViewHolder.ChatUsersAdapter;
import com.collinskesuiabi.diakenya.ViewHolder.ChattingUserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class Fragment_chat extends Fragment {
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference userList,user_chat,chatting;
    RecyclerView recyclerView;
    String phone, department;
    DatabaseDepart localDb;
    ChattingUserAdapter chatAdapter;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE

    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
       Paper.init(getContext());
        Paper.init(getContext());
        Common.Phone_Text = Paper.book().read(Common.USER_KEY);
       database = FirebaseDatabase.getInstance();


        recyclerView = view.findViewById(R.id.recyclerViewChat);
        recyclerView.setHasFixedSize(true);

        //layoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
//        buttonUser = view.findViewById(R.id.addUserChat);
        localDb = new DatabaseDepart(getContext());


        database = FirebaseDatabase.getInstance();
        userList = database.getReference("User");
        //user_chat = database.getReference("DiaUserChart");
        chatting = database.getReference("chatMessages");

      //  getCurrentUser();
        loadUserCharts2();


        return view;


    }
    private void loadUserCharts2() {

        userList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phone = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

                DiaUser user = dataSnapshot.child(phone).getValue(DiaUser.class);
                user.setPhone(phone);

                Common.currentUser = user;

                user = dataSnapshot.child(Common.currentUser.getPhone()).getValue(DiaUser.class);

                assert user != null;
                if (Common.Phone_Text != null){
                    loadOnlinechat();

                }else {
                    assert user != null;


                    Paper.book().write(Common.USER_KEY,Common.currentUser.getPhone());

                    loadOnlinechat();


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void  loadOnlinechat(){

            chatting.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    try {
                        //   userListChat.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ChatMessages chatMessages = snapshot.getValue(ChatMessages.class);


                            if (chatMessages.getSender().equals(Common.currentUser.getPhone()) && !chatMessages.getReceiver().equals(Common.Phone_Text) ) {

                                DataChatMessages messages = new DataChatMessages();
                                String id = String.valueOf(System.currentTimeMillis());
                                messages.setMessageId(chatMessages.getReceiver());
                                messages.setUserPhone(Common.Phone_Text);
                                messages.setMessage(chatMessages.getMessage());
                                messages.setSender(chatMessages.getSender());
                                messages.setReceiver(chatMessages.getReceiver());
                                messages.setDate(chatMessages.getDate());
                                messages.setTime(chatMessages.getTime());


                                if (!localDb.isChatUserDia(chatMessages.getReceiver(), Common.Phone_Text)) {
                                    //Toast.makeText(getContext(), "adding reached ", Toast.LENGTH_SHORT).show();

                                    localDb.addToChatUserDia(messages);
                                    loadUserChat();
                                    // Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();


                                } else {
                                    //Toast.makeText(getContext(), "adding reached ", Toast.LENGTH_SHORT).show();
                                    localDb.removeFromUserChat2(chatMessages.getReceiver(), Common.Phone_Text);
                                    localDb.addToChatUserDia(messages);
                                    loadUserChat();
                                    // Toast.makeText(getContext(), "is already there me receiver", Toast.LENGTH_SHORT).show();
                                }
                            }else if (chatMessages.getReceiver().equals(Common.currentUser.getPhone())&& !chatMessages.getSender().equals(Common.Phone_Text)) {
                                DataChatMessages messages = new DataChatMessages();
                                String id = String.valueOf(System.currentTimeMillis());
                                messages.setMessageId(chatMessages.getSender());
                                messages.setUserPhone(Common.Phone_Text);
                                messages.setMessage(chatMessages.getMessage());
                                messages.setSender(chatMessages.getSender());
                                messages.setReceiver(chatMessages.getReceiver());
                                messages.setDate(chatMessages.getDate());
                                messages.setTime(chatMessages.getTime());

                                if (!localDb.isChatUserDia(chatMessages.getSender(), Common.Phone_Text)) {

                                    localDb.addToChatUserDia(messages);
                                    loadUserChat();
                                   //  Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();


                                } else {
                                    localDb.removeFromUserChat2(chatMessages.getSender(), Common.Phone_Text);
                                    localDb.addToChatUserDia(messages);
                                    loadUserChat();
                                   // Toast.makeText(getContext(), "is already there me sender", Toast.LENGTH_SHORT).show();
                                }
                            }

                            }

                    } catch (Exception e) {
//                    Toast.makeText(getContext(), "am crushing", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



    }
    private void loadUserChat() {
        try {
            chatAdapter = new ChattingUserAdapter(getContext(),new DatabaseDepart(getContext()).getAllChatUserDia(Common.Phone_Text));
            recyclerView.setAdapter(chatAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
///                               Paper.book().write(Common.USER_DEPART,Common.currentUser.getScheduale());




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

