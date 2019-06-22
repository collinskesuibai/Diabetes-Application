package com.collinskesuiabi.diakenya;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Database.DatabaseDepart;
import com.collinskesuiabi.diakenya.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import static java.security.AccessController.getContext;

public class homepage extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 0;
    EditText editText;
    CircleImageView circleImageView;
    LinearLayout linearLayout;
    CardView cardView,cardView1,cardView2,cardView3;
    int sugarValue;
    String strings;

    CardView cardpop1 , cardViewpop2 ,getCardpop3;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);

        try {
            SQLiteDatabase eventsDB = homepage.this.openOrCreateDatabase("Event", MODE_PRIVATE, null);
            eventsDB.execSQL("CREATE TABLE IF NOT EXISTS UserChart(messageId TEXT NOT NULL,UserPhone TEXT NOT NULL,message TEXT,sender TEXT,receiver TEXT,date TEXT,time TEXT,PRIMARY KEY(messageId,UserPhone))");

        } catch (SQLException e) {
            Toast.makeText(homepage.this, "there has been an error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        editText = findViewById(R.id.edittext);
        linearLayout = findViewById(R.id.linearLayout);
        circleImageView = findViewById(R.id.buttonimage);
        cardView = findViewById(R.id.cardlow);
        cardView1 = findViewById(R.id.cardnormal);
        cardView2 = findViewById(R.id.cardpre);
        cardView3 = findViewById(R.id.cardhigh);
        setupBottomNavigationView();




       cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent (homepage.this,Low.class);
               startActivity(intent);
           }
       });
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (homepage.this,Normal.class);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (homepage.this,PreDiabetic.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (homepage.this,Diabetic.class);
                startActivity(intent);
            }
        });








        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().length() >1) {

                    Intent intent = new Intent(homepage.this, Analyse.class);

                    strings = editText.getText().toString();
                    intent.putExtra("Data", strings);
                    startActivity(intent);
                }else{
                    Toast.makeText(homepage.this, "Do not leave the field empty", Toast.LENGTH_SHORT).show();
                }


            }

        });
        getCurrentUser();

    }

    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        bottomNavigationViewEx.setBackgroundColor(getResources().getColor(R.color.black));
        BottomNavigationViewHelper.enableNavigation(homepage.this,this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            // FirebaseAuth.getInstance().signOut();


        }else{
            Intent intent = new Intent(homepage.this,VerifyActivity.class);
            startActivity(intent);

        }
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void getCurrentUser(){
        if(!hasPermissions(homepage.this, PERMISSIONS)){
            ActivityCompat.requestPermissions(homepage.this, PERMISSIONS, PERMISSION_ALL);
        }else {

        }




        //  setupDrawer();




    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(homepage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)

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
}

