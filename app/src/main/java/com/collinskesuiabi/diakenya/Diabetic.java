package com.collinskesuiabi.diakenya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class Diabetic extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        textView = findViewById(R.id.sugarlevel);
        textView.setText("DIABETIC");


        //use a linear layout manager or a gridlayout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // provide the data source
        String[] myDatacountries ={ "1. Exercise Regularly", "2. Control Your Carb Intake", "3. Increase Your Fiber Intake", "4. Drink Water and Stay Hydrated",
                "5. Choose Foods With a Low Glycemic Index", "6. Choose Foods With a Low Glycemic Index", "7. Control Stress Levels", "8. Get Enough Quality Sleep",
                "9. Eat Foods Rich in Chromium and Magnesium", "10. Lose Some Weight"};

        adapter = new MyAdapter(myDatacountries);


        recyclerView.setAdapter(adapter);
    }
}
