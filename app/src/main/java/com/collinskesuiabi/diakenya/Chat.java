package com.collinskesuiabi.diakenya;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.collinskesuiabi.diakenya.Utils.BottomNavigationViewHelper;
import com.collinskesuiabi.diakenya.Utils.SectionsPagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class Chat extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = Chat.this;
    public static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupViewPager();

        setupBottomNavigationView();
    }
    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        bottomNavigationViewEx.setBackgroundColor(getResources().getColor(R.color.black));
        BottomNavigationViewHelper.enableNavigation(Chat.this,this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    public void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_chat()); //index 0
        adapter.addFragment(new Fragment_users());
        adapter.addFragment(new Fragment_doctor());


        //index 1
        mViewPager = (findViewById(R.id.viewpager_container));
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(5);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        tabLayout.getTabAt(0).setText("Chat");
        tabLayout.getTabAt(1).setText("Users");
        tabLayout.getTabAt(2).setText("Doctor");








        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //  tab.getIcon().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //  tab.getIcon().setColorFilter(Color.parseColor("#787678"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {




            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Chat.this ,homepage.class);
        startActivity(intent);
        super.onBackPressed();

    }

}
