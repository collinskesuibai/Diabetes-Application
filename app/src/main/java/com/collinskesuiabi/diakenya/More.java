package com.collinskesuiabi.diakenya;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.Utils.BottomNavigationViewHelper;
import com.collinskesuiabi.diakenya.ViewHolder.ChatUsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class More extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = More.this;
    CircleImageView circleImageView;
    FirebaseDatabase database;
    LinearLayout mLinearProfile;
    LinearLayout mLinearSettings;
    LinearLayout mLinearAbout;
    LinearLayout mLinearShare;
    LinearLayout mLinearHelp;
    String phone;
    int PERMISSION_ALL = 1;
    DatabaseReference table_user;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setTheme(R.style.AppTheme2);
        setContentView(R.layout.activity_more);
        setupBottomNavigationView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        circleImageView = findViewById(R.id.circleImage);
        mLinearProfile = findViewById(R.id.LayoutProfile);
        mLinearSettings = findViewById(R.id.LayoutSettings);
        mLinearAbout = findViewById(R.id.LayoutAbout);
        mLinearShare = findViewById(R.id.LayoutShare);
        mLinearHelp = findViewById(R.id.LayoutHelp);
        database = FirebaseDatabase.getInstance();
        table_user =database.getReference("User");

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this ,GroupImages.class);
                startActivity(intent);
            }
        });


               mLinearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, Profile_User.class);
                startActivity(intent);

            }
        });
        mLinearAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, TextingActivity.class);
                startActivity(intent);

            }
        });
        mLinearHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, Help.class);
                startActivity(intent);

            }
        });
        mLinearSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (More.this,MyData.class);
                startActivity(intent);
            }
        });


        getCurrentUser();
        loadUserCharts2();

    }
    private void loadUserCharts2() {

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phone = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());


                DiaUser user = dataSnapshot.child(phone).getValue(DiaUser.class);
                user.setPhone(phone);

                Common.currentUser = user;

                user = dataSnapshot.child(Common.currentUser.getPhone()).getValue(DiaUser.class);
                assert user != null;
                Picasso.with(More.this).load(Common.currentUser.getImage()).into(circleImageView);






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        bottomNavigationViewEx.setBackgroundColor(getResources().getColor(R.color.black));
        BottomNavigationViewHelper.enableNavigation(mContext,this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(More.this ,homepage.class);
        startActivity(intent);
        super.onBackPressed();

    }
    public void getCurrentUser(){
        if(!hasPermissions(More.this, PERMISSIONS)){
            ActivityCompat.requestPermissions((Activity) More.this, PERMISSIONS, PERMISSION_ALL);
        }else {
            try {
                if(Common.isConnectedInternet(More.this) ){

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
                            Picasso.with(More.this).load(Common.currentUser.getImage()).into(circleImageView);




                            if (Common.Phone_Text != null){

                            }else {
                                assert user != null;

                                Paper.book().write(Common.USER_KEY,Common.currentUser.getPhone());


                            }




                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }else {

                }
            } catch (Exception e) {

                Toast.makeText(More.this, "Check your internet connection", Toast.LENGTH_SHORT).show();


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

}
