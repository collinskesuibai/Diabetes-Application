package com.collinskesuiabi.diakenya;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Model.ChatMessages;
import com.collinskesuiabi.diakenya.Model.DataChatMessages;
import com.collinskesuiabi.diakenya.Model.DiaChatMessages;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.Model.DiaUserChart;

import com.collinskesuiabi.diakenya.ViewHolder.ChatUsersAdapter;
import com.collinskesuiabi.diakenya.ViewHolder.MessageAdapter;
import com.collinskesuiabi.diakenya.ViewHolder.MessageViewHolder;
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
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Inbox_Activity extends AppCompatActivity  {
    FirebaseDatabase database;
    DatabaseReference usersChat, users,chatting;
    String names , phones , images;
    DatabaseReference userList,user_chat;
    TextView textView ;
    CircleImageView circleImageView,circleImageViewsmall;
    ImageView imageView,backButton;
    EditText editText;
    String keyvalue;
    String phone, department;


    LinearLayoutManager linearLayoutManager;

    FloatingActionButton fabMessage;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<DiaChatMessages> mChat = new ArrayList<DiaChatMessages>();

    MessageAdapter messageAdapter;

    FirebaseRecyclerAdapter<ChatMessages,MessageViewHolder> adapter;
    final int WRITE_REQUEST_CODE = 1045;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS


    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        recyclerView = findViewById(R.id.recyclerViewInbox);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        database = FirebaseDatabase.getInstance();

        chatting = database.getReference("chatMessages");

        users =database.getReference("User");
        user_chat = database.getReference("User");
        recyclerView.setHasFixedSize(true);
        linearLayoutManager =new LinearLayoutManager(Inbox_Activity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        names= getIntent().getStringExtra("name");
        phones= getIntent().getStringExtra("phone");
        images= getIntent().getStringExtra("image");
        editText = findViewById(R.id.editMessage);
        fabMessage = findViewById(R.id.fabSend);
        textView = findViewById(R.id.textUserName);
        circleImageView = findViewById(R.id.circleImageUser);
        imageView = findViewById(R.id.backButton);
        Picasso.with(this).load(images).into(circleImageView);
        textView.setText(names);
        getCurrentUser();
        loadUserCharts2();

        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                final StringBuilder date = new StringBuilder(
                        android.text.format.DateFormat.format("dd-MM-yyyy HH:mm",calendar).toString());
                final StringBuilder datePlaced = new StringBuilder(
                        android.text.format.DateFormat.format("HH:mm",calendar).toString());
                keyvalue = String.valueOf(System.currentTimeMillis());
              ChatMessages userMessage =new ChatMessages (editText.getText().toString(),Common.currentUser.getPhone(),phones,datePlaced.toString(),"08:00");
              chatting.push().setValue(userMessage);
              editText.setText("");


            }
        });





    }

    private void loadUserCharts2() {

        user_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                phone = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                DiaUser user = dataSnapshot.child(phone).getValue(DiaUser.class);
                user.setPhone(phone);

                Common.currentUser = user;

                user = dataSnapshot.child(Common.currentUser.getPhone()).getValue(DiaUser.class);

                assert  user !=null;
                loadUserMessages();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getCurrentUser(){
        if(!hasPermissions(Inbox_Activity.this, PERMISSIONS)){
            ActivityCompat.requestPermissions((Activity) Inbox_Activity.this, PERMISSIONS, PERMISSION_ALL);
        }else {
            try {
                if(Common.isConnectedInternet(Inbox_Activity.this) ){

                    phone = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference table_user = database.getReference("User");






                    users.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            DiaUser user = dataSnapshot.child(phone).getValue(DiaUser.class);
                            user.setPhone(phone);

                            Common.currentUser = user;

                            user = dataSnapshot.child(Common.currentUser.getPhone()).getValue(DiaUser.class);


                            assert user != null;
                            loadUserMessages();
                            Picasso.with(Inbox_Activity.this).load(Common.currentUser.getImage()).into(circleImageView);





                            if (Common.Phone_Text != null){

                            }else {
                                assert user != null;

                                Paper.book().write(Common.USER_KEY,Common.currentUser.getPhone());


                            }
                            loadUserMessages();




                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }else {
                    loadUserMessages();

                }
            } catch (Exception e) {

                Toast.makeText(Inbox_Activity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();


                e.printStackTrace();
            }



        }




        //  setupDrawer();




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(Inbox_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)


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


    private void loadUserMessages() {

        chatting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    DiaChatMessages chatMessages = snapshot.getValue(DiaChatMessages.class);
                   if (chatMessages.getReceiver().equals(Common.currentUser.getPhone()) && chatMessages.getSender().equals(phones) ||
                            chatMessages.getReceiver().equals(phones) && chatMessages.getSender().equals(Common.currentUser.getPhone())){

                        mChat.add(chatMessages);

                    }


                }
                messageAdapter = new MessageAdapter(Inbox_Activity.this, mChat);
                recyclerView.setAdapter(messageAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
