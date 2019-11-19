package com.example.bca_messenger;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URI;

public class settingsActivity extends AppCompatActivity {
    private TextInputEditText nameCS;
    private TextInputEditText emailcS;
    private TextInputEditText bioCS;
    private FloatingActionButton camera;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private final static int code=1;
    private Uri imageUri=null ;
    private StorageReference storage;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        nameCS=(TextInputEditText)findViewById(R.id.nameCS);
        emailcS=(TextInputEditText)findViewById(R.id.emailCS);
        bioCS=(TextInputEditText)findViewById(R.id.bioCS);
        save=(Button)findViewById(R.id.saveCS);
        camera=(FloatingActionButton) findViewById(R.id.camera);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("USER DETAILS");
        auth=FirebaseAuth.getInstance();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("images/*");
                startActivityForResult(galleryIntent,code);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
            }
        });
    }

    private void saveDetails() {
        final String mName=nameCS.getText().toString().trim();
        final String mBio=bioCS.getText().toString().trim();
        if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mBio)){
            StorageReference imagepath = storage.child("Profile").child(imageUri.getLastPathSegment());
            imagepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String userId=auth.getCurrentUser().getUid();
                    DatabaseReference userDB = databaseReference.child(userId);
                    userDB.child("name").setValue(mName);
                    userDB.child("bio").setValue(mBio);
                    userDB.child("image").setValue(imageUri.toString());

                }
            });
            Toast.makeText(this,"Profile Edited",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == code && resultCode == RESULT_OK){
            Uri mImageUri= data.getData();
            CropImage.activity(mImageUri).
                    setAspectRatio(1,1).
                    setGuidelines(CropImageView.Guidelines.ON).
                    start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode == RESULT_OK){
                imageUri=result.getUri();
                camera.setImageURI(imageUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception exception=result.getError();
            }
        }
    }
}
