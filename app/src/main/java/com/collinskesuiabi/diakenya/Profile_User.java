package com.collinskesuiabi.diakenya;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Profile_User extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView userName,studentsId,PhoneNumber,Roll;
    RelativeLayout relativeLayout,relativeName;

    FirebaseDatabase database;
    DatabaseReference requests;
    FirebaseDatabase databases;
    String phone;
    DatabaseReference table_user;
    ImageView BackButton;
    private Context mContext = Profile_User.this;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);

        circleImageView = findViewById(R.id.circleImage);
        userName = findViewById(R.id.edtUserName);
        studentsId = findViewById(R.id.SchoolId);
        PhoneNumber = findViewById(R.id.PhoneNumber);
        Roll =findViewById(R.id.roll);
        relativeName = findViewById(R.id.RealtiveName);
        databases = FirebaseDatabase.getInstance();
        table_user =databases.getReference("User");
        BackButton = findViewById(R.id.backButton);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_User.this ,GroupImages.class);
                startActivity(intent);
            }
        });
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_User.this ,More.class);
                startActivity(intent);
            }
        });
        if (Common.currentUser == null) {

            getCurrentUser();

        }else {

        }

        table_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DiaUser user = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).getValue(DiaUser.class);
                user.setPhone(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                try {
                    Picasso.with(Profile_User.this).load(user.getImage()).into(circleImageView);
                    userName.setText(user.getName());
                    studentsId.setText(user.getId());
                    PhoneNumber.setText(user.getPhone());
                    Roll.setText(user.getPerson());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Common.currentUser = user;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        relativeLayout =findViewById(R.id.relativeLayout);

//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Profile_User.this,User_Register.class);
//
////                Pair[] pairs = new Pair[1];
////
////                pairs[0]  = new Pair<View,String>(relativeLayout,"imageTransition");
////
////                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Profile_User.this,
////                        pairs);
//
//                startActivity(intent);
//
//            }
//        });
        relativeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_User.this, User_Name.class);
                startActivity(intent);
            }
        });




    }
    public void getCurrentUser(){
        if(!hasPermissions(Profile_User.this, PERMISSIONS)){
            ActivityCompat.requestPermissions((Activity) Profile_User.this, PERMISSIONS, PERMISSION_ALL);
        }else {
            try {
                if(Common.isConnectedInternet(Profile_User.this) ){

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

                Toast.makeText(Profile_User.this, "Check your internet connection", Toast.LENGTH_SHORT).show();


                e.printStackTrace();
            }



        }




        //  setupDrawer();




    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Profile_User.this ,More.class);
        startActivity(intent);
        super.onBackPressed();

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
