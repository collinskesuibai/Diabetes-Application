package com.collinskesuiabi.diakenya;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.Model.Groups;
import com.collinskesuiabi.diakenya.ViewHolder.GroupViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.UUID;

public class GroupImages extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference imageList,userList;
    FirebaseRecyclerAdapter<Groups, GroupViewHolder> adapter;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri saveUri;
    MaterialEditText materialEditTextName,materialEditTextweb;
    Groups groups;
    ProgressBar progressBar;








    Button ButtonUpload , ButtonSelect;
    Spinner GroupSpinner ,SubGroupSpinner;
    String groupName,UriImage;
    FloatingActionButton floatingActionButton,floatingActionButton1;
    final String[] Group = {"Movies and Shows","Animation", "Art" ,"Cars","Nature" ,"Animals","Technology","Business","Emojis","ClipArt","People"};


    private final int PICK_IMAGE_REQUEST =71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_images);
        recyclerView = findViewById(R.id.recyclerGroupImages);
        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(GroupImages.this);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        database = FirebaseDatabase.getInstance();
        imageList = database.getReference("Group");
        userList = database.getReference("User");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        materialEditTextName =findViewById(R.id.edtUploadImage);
        materialEditTextweb = findViewById(R.id.edtGroupWeb);
        ButtonSelect = findViewById(R.id.btnSelect);
        ButtonUpload = findViewById(R.id.btnUpload);
        GroupSpinner =findViewById(R.id.spinnerGroup);
        progressBar = findViewById(R.id.progressImage);
floatingActionButton= findViewById(R.id.fabGroupImage);
floatingActionButton1 =findViewById(R.id.fabGroupWeb);
floatingActionButton1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showWebDialog();
    }
});
floatingActionButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showUniDialog();
    }
});
LoadGroups();

    }
    public void LoadGroups (){
        FirebaseRecyclerOptions<Groups> options =new FirebaseRecyclerOptions.Builder<Groups>()
                .setQuery(imageList,Groups.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Groups, GroupViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GroupViewHolder holder, int position, @NonNull final Groups model) {
                holder.txtTitle.setText(model.getName());
                Picasso.with(GroupImages.this).load(model.getGroupImage()).into(holder.imageGroup);
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(GroupImages.this,SubGroupImages.class);
                        intent.putExtra("GroupId",model.getGroupId());
                        startActivity(intent);
//                        DiaUser user =new DiaUser (Common.currentUser.getName(),Common.currentUser.getPhone(),"Student",Common.currentUser.getSchool(),"kes",Common.currentUser.getUniversity(),Common.currentUser.getDepartment(),Common.currentUser.getId(),Common.currentUser.getImage());
//                        userList.child(Common.currentUser.getPhone()).setValue(user);
                        }

                });
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View layout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.groupimage,parent , false);
                return  new GroupViewHolder(layout);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    public void showUniDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this));
        alertDialog.setTitle("Add your ImageGroup");


        LayoutInflater inflater =this.getLayoutInflater();
        View add_menu_layout =inflater.inflate(R.layout.add_new_group,null);


        ButtonSelect =add_menu_layout.findViewById(R.id.btnSelect);
        ButtonUpload =add_menu_layout.findViewById(R.id.btnUpload);
        GroupSpinner = add_menu_layout.findViewById(R.id.spinnerGroup);

        GroupSpinner = add_menu_layout.findViewById(R.id.spinnerGroup);

        GroupSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Group));

        ButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        ButtonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String order_number  = String.valueOf(System.currentTimeMillis());
                groups = new Groups(UriImage,Group[GroupSpinner.getSelectedItemPosition()],order_number);
                imageList.push().setValue(groups);




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
    public void showWebDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this));
        alertDialog.setTitle("Add your ImageGroup");


        LayoutInflater inflater =this.getLayoutInflater();
        View add_menu_layout =inflater.inflate(R.layout.add_new_web,null);



        GroupSpinner = add_menu_layout.findViewById(R.id.spinnerGroup);

        GroupSpinner = add_menu_layout.findViewById(R.id.spinnerGroup);

        GroupSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Group));

        materialEditTextweb = add_menu_layout.findViewById(R.id.edtGroupWeb);






        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String order_number  = String.valueOf(System.currentTimeMillis());
                groups = new Groups(materialEditTextweb.getText().toString(),Group[GroupSpinner.getSelectedItemPosition()],order_number);
                imageList.push().setValue(groups);




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
    private void uploadImage() {
        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Uploading....");
        mDialog.show();

        String imageName = UUID.randomUUID().toString();
        final StorageReference imageFolder = storageReference.child("images/"+imageName);
        imageFolder.putFile(saveUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mDialog.dismiss();
                        Toast.makeText(GroupImages.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                UriImage = uri.toString();

//                                image = new Image(Desciption.getText().toString(),location.getText().toString(),uri.toString(), Common.currentUser.getUniversity());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mDialog.dismiss();
                        Toast.makeText(GroupImages.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress =(100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        mDialog.setMessage("Uploaded " + progress +"%");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data !=null && data.getData() !=null){
            saveUri =data.getData();
            ButtonSelect.setText("Image Selected");
        }
    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);


    }

}
