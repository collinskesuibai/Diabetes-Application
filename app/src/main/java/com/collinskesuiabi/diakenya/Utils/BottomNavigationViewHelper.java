package com.collinskesuiabi.diakenya.Utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;


import com.collinskesuiabi.diakenya.Chat;
import com.collinskesuiabi.diakenya.More;
import com.collinskesuiabi.diakenya.R;

import com.collinskesuiabi.diakenya.homepage;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class
BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(true);
       // bottomNavigationViewEx.setBackgroundColor(R.color.red);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);

        bottomNavigationViewEx.setTextVisibility(true);

    }
    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_house:
                        Intent intent1 = new Intent(context, homepage.class);//ACTIiVITY_NUM = 0

                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                        break;

                    case R.id.ic_message:
                        Intent message  = new Intent(context, Chat.class);//ACTIVITY_NUM = 1
                        context.startActivity(message);
                        callingActivity.overridePendingTransition(R.anim.fade_in ,R.anim.fade_out);
                        break;


                    case R.id.ic_more:
                        Intent intent3 = new Intent(context, More.class);//ACTIVITY_NUM =2

                            context.startActivity(intent3);
                            callingActivity.overridePendingTransition(R.anim.fade_in ,R.anim.fade_out);
                        break;

                }


                return false;
            }
        });
    }
}


