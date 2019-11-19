package com.example.bca_messenger;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sttngs extends AppCompatActivity {
    private TextView account;
    private TextView psswd;
    private TextView admin;
    private TextView out;
    private ImageView imageView;
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sttngs);
        account=(TextView) findViewById(R.id.account);
        psswd=(TextView) findViewById(R.id.psswd);
        admin=(TextView)findViewById(R.id.admin);
        out=(TextView)findViewById(R.id.out);
        imageView=(ImageView)findViewById(R.id.circleimage);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        String mail =user.getEmail().toString();
        String mail1="abc@xyz.com";
        if (mail.matches(mail1)){
            admin.setVisibility(View.VISIBLE);
        }
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sttngs.this,account.class));
            }
        });
        psswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sttngs.this,pass.class));
            }
        });

    }
}

