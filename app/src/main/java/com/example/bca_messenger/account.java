package com.example.bca_messenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class account extends AppCompatActivity implements bottomSheet.BottomSheetListner{
    private ImageView dp;
    private FloatingActionButton camera;
    private TextView name;
    private TextView about;
    private TextView mail;
    private ImageView edit1;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private static final int code=1;
    private Uri imageUri=null ;
    private StorageReference storage;
    private Button save;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;
    private ProgressDialog progress;
    List<account_details> chatDataList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        save=(Button)findViewById(R.id.save);
        dp=(ImageView)findViewById(R.id.dp);
        camera=(FloatingActionButton)findViewById(R.id.camera);
        name=(TextView)findViewById(R.id.profileName);
        about=(TextView)findViewById(R.id.profileAbout);
        mail=(TextView)findViewById(R.id.profilemail);
        edit1=(ImageView)findViewById(R.id.edit);
        database=FirebaseDatabase.getInstance();
        progress = new ProgressDialog(this);
        ref=database.getReference().child("Profile");
        auth=FirebaseAuth.getInstance();
        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet sheet = new bottomSheet();
                sheet.show(getSupportFragmentManager(),"Hello");
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,code);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub();
            }
        });
        show();

    }

    private void show() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    account_details tm=ds.getValue(account_details.class);
                   // chatDataList.add(new account_details(tm.username,tm.usermail,tm.about,tm.imageResource));
                    String ml1=tm.usermail;
                    String ml=user.getEmail();
                    if (ml.equals(ml1)){
                        String img = tm.getImageResource();
                        Picasso.get().load(img).into(dp);
                        name.setText(tm.username);
                        about.setText(tm.about);
                        mail.setText(tm.getUsermail());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sub() {
        progress.setMessage("Posting.....");
        progress.show();
        final FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        storage=FirebaseStorage.getInstance().getReference().child("dp").child(imageUri.getLastPathSegment());
        String id=ref.push().getKey();
        storage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String nm=name.getText().toString();
                String ab=about.getText().toString();
                String mail =user1.getEmail().toString();
                String downurl=taskSnapshot.getDownloadUrl().toString();
                DatabaseReference pro=ref.push();
                /*Map<String, String> datatosave =new HashMap<>();
                datatosave.put("name",nm);
                datatosave.put("mail",mail);
                datatosave.put("about",ab);
                datatosave.put("dp",downurl.toString());*/
                testModel tm=new testModel(nm,mail,ab,downurl.toString());
                pro.setValue(tm);
                progress.dismiss();

            }
        });

    }

    @Override
    public void onclicked(String text1, String text2) {
        name.setText(text1);
        about.setText(text2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code && resultCode == RESULT_OK) {
            Uri image= data.getData();
            CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).start(account.this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                dp.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
