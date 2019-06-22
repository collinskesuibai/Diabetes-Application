package com.collinskesuiabi.diakenya;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.rengwuxian.materialedittext.MaterialEditText;

public class User_Name extends AppCompatActivity {

    String numbers;
    Button buttons,CancelButton;
    TextView textViews;
    MaterialEditText materialEditText;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
     Editable name;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__name);

        materialEditText= findViewById(R.id.edtUserName);

         buttons  =findViewById(R.id.ButtonOk);
         CancelButton = findViewById(R.id.buttonCancel);
        textViews =findViewById(R.id.textViewUserName);
        mAuth = FirebaseAuth.getInstance();

       numbers = mAuth.getCurrentUser().getPhoneNumber();

        try {
            materialEditText.append(Common.currentUser.getName());
            materialEditText.setSelection(materialEditText.length());

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(materialEditText, InputMethodManager.SHOW_IMPLICIT);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            materialEditText.setFocusable(true);

            //Hide keyboard

//            InputMethodManager inputMethodManager1 =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager1.hideSoftInputFromInputMethod(materialEditText.getWindowToken(),0);

        } catch (Exception e) {
            e.printStackTrace();
        }


        database = FirebaseDatabase.getInstance();
        table_user =database.getReference("User");
        table_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DiaUser user = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).getValue(DiaUser.class);
                user.setPhone(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Common.currentUser = user;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Name.this , Profile_User.class);
                startActivity(intent);
            }
        });

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name= materialEditText.getText();



                if (name.length()>0){
                    DiaUser user =new DiaUser (materialEditText.getText().toString(),numbers,Common.currentUser.getPerson(),Common.currentUser.getId(),Common.currentUser.getImage());
                    table_user.child(numbers).setValue(user);

                    Intent intent = new Intent(User_Name.this , Profile_User.class);
                    startActivity(intent);


                    }else{
                    Toast.makeText(User_Name.this, "UserName field cannot be empty", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}
