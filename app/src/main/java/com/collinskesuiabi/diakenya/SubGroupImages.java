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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Interface.ItemClickListener;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.collinskesuiabi.diakenya.Model.SubGroup;
import com.collinskesuiabi.diakenya.ViewHolder.SubGroupViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubGroupImages extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference imageList,userList;
    FirebaseRecyclerAdapter<SubGroup, SubGroupViewHolder> adapter;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri saveUri;
    MaterialEditText materialEditTextSubName,materialEditTextWeb;
    SubGroup groups;
    CircleImageView circleImageView;
    Button ButtonUpload , ButtonSelect;

    String groupName,UriImage,GroupId;

    FloatingActionButton floatingActionButton,floatingActionButton1;
    final String[] Group = {"Movies and Shows","Animation", "Art" ,"Nature" ,"Animals","Technology","Business","Emojis","ClipArt","People"};


    private final int PICK_IMAGE_REQUEST =71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_group_images);
        recyclerView = findViewById(R.id.recyclerSubGroup);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(SubGroupImages.this);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        database = FirebaseDatabase.getInstance();
        imageList = database.getReference("SubGroup");
        userList = database.getReference("User");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        ButtonSelect = findViewById(R.id.btnSelect);
        ButtonUpload = findViewById(R.id.btnUpload);
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
        GroupId= getIntent().getStringExtra("GroupId");


        LoadGroups();

    }

    private void showWebDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this));
        alertDialog.setTitle("Add your SubGroup");


        LayoutInflater inflater =this.getLayoutInflater();
        View add_menu_layout =inflater.inflate(R.layout.add_new_web_group,null);


        materialEditTextWeb = add_menu_layout.findViewById(R.id.edtSubGroupWeb);
        materialEditTextSubName =add_menu_layout.findViewById(R.id.edtSubGroup);




        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String order_number  = String.valueOf(System.currentTimeMillis());
                groups = new SubGroup(materialEditTextWeb.getText().toString(),GroupId,order_number,materialEditTextSubName.getText().toString());
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

    public void LoadGroups (){
        Query listOfSubGroups = imageList.orderByChild("groupId").equalTo(GroupId);
        Log.d("collins ","this method was called");
        FirebaseRecyclerOptions<SubGroup> options =new FirebaseRecyclerOptions.Builder<SubGroup>()
                .setQuery(listOfSubGroups,SubGroup.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<SubGroup, SubGroupViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SubGroupViewHolder holder, int position, @NonNull final SubGroup model) {
                holder.txtTitle.setText(model.getName());
                Picasso.with(SubGroupImages.this).load(model.getImages()).into(holder.imageSubGroup);
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        showImageDialog(model.getImages());

                    }

                });
            }

            @Override
            public SubGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View layout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_group_item,parent , false);
                return  new SubGroupViewHolder(layout);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    public void showUniDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this));
        alertDialog.setTitle("Add your SubGroup");


        LayoutInflater inflater =this.getLayoutInflater();
        View add_menu_layout =inflater.inflate(R.layout.add_new_sub_group,null);


        ButtonSelect =add_menu_layout.findViewById(R.id.btnSelect);
        ButtonUpload =add_menu_layout.findViewById(R.id.btnUpload);
        materialEditTextSubName =add_menu_layout.findViewById(R.id.edtSubGroup);



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
                groups = new SubGroup(UriImage,GroupId,order_number,materialEditTextSubName.getText().toString());
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
                        Toast.makeText(SubGroupImages.this, "Uploaded", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SubGroupImages.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void showImageDialog(final String images){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this));
        alertDialog.setTitle("Profile Photo");
        alertDialog.setMessage("Do you want to use this Image As your Profile Picture ?");
        LayoutInflater inflater =this.getLayoutInflater();
        View add_menu_layout =inflater.inflate(R.layout.imageprofile,null);
        circleImageView = add_menu_layout.findViewById(R.id.circleImage);
        Picasso.with(SubGroupImages.this).load(images).into(circleImageView);



        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DiaUser user =new DiaUser (Common.currentUser.getName(),Common.currentUser.getPhone(),Common.currentUser.getPerson(),Common.currentUser.getId(),images);
                userList.child(Common.currentUser.getPhone()).setValue(user);
                Intent intent= new Intent(SubGroupImages.this , Profile_User.class);
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

}


