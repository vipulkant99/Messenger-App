package com.example.bca_messenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class testActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText usr_msg;
    private FloatingActionButton send;
    String time,date,name,mail;
    DatabaseReference dref;
    private FirebaseUser user1;
    private FirebaseAuth auth;
    ProgressDialog pd;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth.AuthStateListener listener;
    RecyclerView.Adapter adapter;
    List<testModel> chatDataList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        dref= FirebaseDatabase.getInstance().getReference().child("BCA 1st Year");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name=user.getDisplayName();
        mail=user.getEmail();
        auth=FirebaseAuth.getInstance();
        user1=auth.getCurrentUser();
        usr_msg=(EditText) findViewById(R.id.etMessage);
        send=(FloatingActionButton) findViewById(R.id.buttonSent);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDetails();
            }
        });

        showChats();
        /*chatDataList.clear();
        adapter.notifyDataSetChanged();*/


    }
    private void showChats(){

        pd=new ProgressDialog(testActivity.this);
        pd.setMessage("Loding Details");
        pd.setCancelable(false);
        // pd.show();
        layoutManager=new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    testModel tm=ds.getValue(testModel.class);
                    chatDataList.add(new testModel(tm.username,tm.usermail,tm.message,tm.time,tm.date,R.mipmap.ic_launcher));
                }
                adapter=new testAdapter(chatDataList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


/*
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    testModel tm=ds.getValue(testModel.class);
                    chatDataList.add(new testModel(tm.username,tm.usermail,tm.message,tm.time,tm.date,R.mipmap.ic_launcher));
                }
                adapter=new testAdapter(chatDataList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void submitDetails(){
        time=getCurrentTime();
        date=getCurrentDate();
        String id=dref.push().getKey();
        String message=usr_msg.getText().toString().trim();

        testModel tm=new testModel(name,mail,message,time,date);
        dref.child(id).setValue(tm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Sent",Toast.LENGTH_SHORT).show();
                usr_msg.getText().clear();
                chatDataList.clear();
                adapter.notifyDataSetChanged();
                showChats();
            }
        });

    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
        String strDate = mdformat.format(calendar.getTime());
        //display(strDate);
        return strDate;

    }

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strTime = mdformat.format(calendar.getTime());
        return strTime;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.you) {
            if (user1 != null && auth != null) {
                auth.signOut();
                startActivity(new Intent(testActivity.this, logIn.class));
                finish();
            }
        }
        else if (item.getItemId()==R.id.profile){
            startActivity(new Intent(testActivity.this,sttngs.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener!=null){
            auth.removeAuthStateListener(listener);
        }
    }
}
