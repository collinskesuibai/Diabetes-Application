package com.collinskesuiabi.diakenya;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class home extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);


        //use a linear layout manager or a gridlayout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // provide the data source
        String[] myDatacountries ={ "Belarus", "Belgium", "Belize", "Benin",
                "Bhutan", "Bolivia", "Bosnia And Herzegovina", "Botswana",
                "Brazil", "Brunei Darussalam"};

        String[] myImages ={"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        ,"https://d1qb2nb5cznatu.cloudfront.net/startups/i/60436-22967c4ce89dbdbd4a8d49b090509a78-medium_jpg.jpg?buster=1517982458"
        };
        String[] myDataDescription ={"These are countries ","These are countries ","These are countries ","These are countries ","These are countries ","These are countries ","These are countries ","These are countries ","These are countries ","These are countries "};

        adapter = new MyAdapter(myDatacountries);


        recyclerView.setAdapter(adapter);
    }
}
