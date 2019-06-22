package com.collinskesuiabi.diakenya;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btnsighnin, btnsignUp;
    TextView slogan;
    private ViewPager mSlideViewPager;
    private LinearLayout linearLayout;

    private TextView [] mDots;
    private SliderAdapter sliderAdapter;
    private Button back, Next;
    private int mCurrentPage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        mSlideViewPager = findViewById( R.id.viewPager);
        sliderAdapter = new SliderAdapter(MainActivity.this);
        mSlideViewPager.setAdapter(sliderAdapter);
        back = findViewById(R.id.button2);
        Next = findViewById(R.id.button1);




        linearLayout = findViewById(R.id.dotsLayout);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this,homepage.class);
            startActivity(intent);
            finish();
            // FirebaseAuth.getInstance().signOut();

        }else{
            addDotsIndicator(0);
            mSlideViewPager.addOnPageChangeListener(viewListener);




            Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSlideViewPager.setCurrentItem(mCurrentPage +1);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSlideViewPager.setCurrentItem(mCurrentPage-1);
                }
            });



        }




    }
    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        linearLayout.removeAllViews();
        for (int i = 0; i<mDots.length ; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.overlayBack));

            linearLayout.addView(mDots[i]);
        }
        if (mDots.length >0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;

            if (position == 0){
                Next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);

                Next.setText("Next");
                back.setText("");

            }else if (position ==mDots.length - 1){

                Next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                Next.setText("Finish");
                back.setText("Back");
                Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            // FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this,homepage.class);
                            startActivity(intent);

                        }else{
                            Intent intent = new Intent(MainActivity.this,VerifyActivity.class);
                            startActivity(intent);

                        }
                        Intent intent = new Intent(MainActivity.this,homepage.class);
                        startActivity(intent);
                    }
                });
            }else {
                Next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                Next.setText("Next");
                back.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this,homepage.class);
            startActivity(intent);
            // FirebaseAuth.getInstance().signOut();


        }else{


        }
    }


}
