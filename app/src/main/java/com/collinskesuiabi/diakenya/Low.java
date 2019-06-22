package com.collinskesuiabi.diakenya;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Model.ChatMessages;
import com.collinskesuiabi.diakenya.Model.DiaData;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.Model.SubGroup;
import com.collinskesuiabi.diakenya.ViewHolder.ChatUsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import io.paperdb.Paper;

public class Low extends AppCompatActivity {
    DatabaseReference userList,userDatas,chatting;
    FirebaseDatabase database;
    String phone;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    FloatingActionButton floatingActionButton;

    TextView textView,numbers ;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Paper.init(Low.this);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        textView = findViewById(R.id.sugarlevel);
        textView.setText("LOW");
        data= getIntent().getStringExtra("Data");
        database = FirebaseDatabase.getInstance();
        userList = database.getReference("User");
        userDatas = database.getReference("DiaData");

        floatingActionButton = findViewById(R.id.btnsend);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Low.this , Chat.class);
                startActivity(intent);

            }
        });


        //use a linear layout manager or a gridlayout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // provide the data source
        String[] myDatacountries ={ "Preventing Low Blood Sugar \n  \n1. When you exercise, check your blood sugar levels. Make sure you have snacks with you \n \n" +
                "2. Talk to your provider about reducing insulin doses on days that you exercise. \n  \n" +
                "3. Ask your provider if you need a bedtime snack to prevent low blood sugar overnight. Protein snacks may be best " +
                "\n \n 4. DO NOT drink alcohol without eating food. Women should limit alcohol to 1 drink a day and men should limit alcohol to 2 drinks a day. Family and friends should know how to help. They should know: \n  •\tThe symptoms of low blood sugar and how to tell if you have them.\n" +
                        "•\tHow much and what kind of food they should give you.\n" +
                        "•\tWhen to call for emergency help.\n" +
                        "•\tHow to inject glucagon, a hormone that increases your blood sugar. Your doctor or nurse will tell you when to use this medicine.\n",
                "The most common causes of low blood sugar are:\n \n" +
                        "•\tTaking your insulin or diabetes medicine at the wrong time\n" +
                        "•\tTaking too much insulin or diabetes medicine\n" +
                        "•\tNot eating enough during meals or snacks after you have taken insulin or diabetes medicine\n" +
                        "•\tSkipping meals\n" +
                        "•\tWaiting too long after taking your medicine to eat your meals\n" +
                        "•\tExercising a lot or at a time that is unusual for you\n" +
                        "•\tNot checking your blood sugar or not adjusting your insulin dose before exercising\n" +
                        "•\tDrinking alcohol\n"};

        adapter = new MyAdapter(myDatacountries);



        recyclerView.setAdapter(adapter);
        showImageDialog();
        loadUserCharts2();
    }
    public void showImageDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this));
        alertDialog.setTitle("Profile Photo");
        alertDialog.setMessage("Do you want to contact a doctor you blood sugar level is low ?");
        LayoutInflater inflater =this.getLayoutInflater();
        View add_menu_layout =inflater.inflate(R.layout.patientprofile,null);
        numbers = add_menu_layout.findViewById(R.id.ValueReading);
        numbers.setText(data);





        alertDialog.setView(add_menu_layout);


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent= new Intent(Low.this , Chat.class);
                startActivity(intent);

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        alertDialog.show();


    }
    private void loadUserCharts2() {



       userList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phone = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

                DiaUser user = dataSnapshot.child(phone).getValue(DiaUser.class);
                user.setPhone(phone);


                Common.currentUser = user;

                user = dataSnapshot.child(Common.currentUser.getPhone()).getValue(DiaUser.class);
                if (Common.Phone_Text != null){
                    if (Common.currentUser.getPhone() !=null)
                    {
                        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                        final StringBuilder date = new StringBuilder(
                                android.text.format.DateFormat.format("dd-MM-yyyy HH:mm",calendar).toString());
                        final StringBuilder datePlaced = new StringBuilder(
                                android.text.format.DateFormat.format("HH:mm",calendar).toString());
                        DiaData userData =new DiaData (date.toString(),Common.currentUser.getPhone(),data);
                        userDatas.push().setValue(userData);

                    }



                }else {
                    assert user != null;

                    Paper.book().write(Common.USER_KEY,Common.currentUser.getPhone());
                    if (Common.currentUser.getPhone() !=null)
                    {
                        DiaData userData =new DiaData (Common.currentUser.getName(),Common.currentUser.getPhone(),data);
                        userDatas.push().setValue(userData);

                    }




                }






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
