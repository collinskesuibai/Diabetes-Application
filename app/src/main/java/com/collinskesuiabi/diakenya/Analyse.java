package com.collinskesuiabi.diakenya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Analyse extends AppCompatActivity {
    CardView cardpop1 , cardViewpop2 ,getCardpop3;
   float sugarValue;
   String strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);
        strings = getIntent().getStringExtra("Data");
        sugarValue =Float.parseFloat(strings);




                cardpop1 = findViewById(R.id.cardpop1);
                cardViewpop2 = findViewById(R.id.cardpop2);
                getCardpop3 = findViewById(R.id.cardpop3);

        cardpop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sugarValue < 70){

                    Intent intent = new Intent (Analyse.this,Low.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }else if (sugarValue>70 && sugarValue<100){
                    Intent intent = new Intent (Analyse.this,Normal.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }else if (sugarValue>100 && sugarValue<125){
                    Intent intent = new Intent (Analyse.this,PreDiabetic.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }else if (sugarValue>126){
                    Intent intent = new Intent (Analyse.this,Diabetic.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }

            }
        });
        cardViewpop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sugarValue < 70){
                    Intent intent = new Intent (Analyse.this,Low.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }else if (sugarValue>70 && sugarValue<140){
                    Intent intent = new Intent (Analyse.this,Normal.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }else if (sugarValue>140 && sugarValue<200){
                    Intent intent = new Intent (Analyse.this,PreDiabetic.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }else if (sugarValue>200){
                    Intent intent = new Intent (Analyse.this,Diabetic.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }

            }
        });
        getCardpop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sugarValue < 70){
                    Intent intent = new Intent (Analyse.this,Low.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);

                }else if (sugarValue>70 && sugarValue<200){
                    Intent intent = new Intent (Analyse.this,Normal.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);


                }else if (sugarValue>200){
                    Intent intent = new Intent (Analyse.this,Diabetic.class);
                    intent.putExtra("Data",strings);
                    startActivity(intent);
                }

            }
        });

    }
}
