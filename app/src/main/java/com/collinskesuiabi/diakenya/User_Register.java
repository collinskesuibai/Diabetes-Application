package com.collinskesuiabi.diakenya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class User_Register extends AppCompatActivity {

    String numbers;
    Button buttons;
    TextView textViews;
    MaterialEditText materialEditText;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
     Editable name;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register);

        materialEditText= findViewById(R.id.edtUserName);

         buttons  =findViewById(R.id.buttonUserName);
        textViews =findViewById(R.id.textViewUserName);
        mAuth = FirebaseAuth.getInstance();

       numbers = mAuth.getCurrentUser().getPhoneNumber();

        try {
            materialEditText.setText(Common.currentUser.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        database = FirebaseDatabase.getInstance();
        table_user =database.getReference("User");

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name= materialEditText.getText();



                if (name.length()>0){
                    DiaUser user =new DiaUser (materialEditText.getText().toString(),numbers,"Student","moi","kes");
                    table_user.child(numbers).setValue(user);

                    Intent intent = new Intent(User_Register.this , homepage.class);
                    startActivity(intent);

                    }else{
                    Toast.makeText(User_Register.this, "UserName field cannot be empty", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}
