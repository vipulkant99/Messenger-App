package com.example.bca_messenger;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class logIn extends AppCompatActivity {

    private TextInputEditText email;
    public TextInputEditText password;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listner;
    private FirebaseUser user1;
    private Button login;
    private TextInputLayout passwordLayout;
    private LinearLayout ll;
    private ProgressDialog progressDialog;
    private CardView cdv;
    public String old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        progressDialog=new ProgressDialog(this);
        email=(TextInputEditText) findViewById(R.id.email);
        password=(TextInputEditText) findViewById(R.id.password);
        ll=(LinearLayout) findViewById(R.id.linearLayout);
        login=(Button)findViewById(R.id.login);
        passwordLayout=(TextInputLayout) findViewById(R.id.passwordLayout);
        old=password.getText().toString();

        cdv=(CardView) findViewById(R.id.cardView);
        cdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closek();
            }
        });

        auth = FirebaseAuth.getInstance();
        listner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user1=firebaseAuth.getCurrentUser();
                if(user1!=null){
                    //Toast.makeText(logIn.this,"logged in",Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(logIn.this,chatActivity.class));
                    /*progressDialog.setMessage("Loging In...");
                    progressDialog.show();*/

                }
                else{
                    Toast.makeText(logIn.this,"Not Logged in",Toast.LENGTH_LONG).show();
                }


            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closek();
                if(!validateEmail() | !validatePassword()){
                    /*String mail = email.getText().toString();
                    String pwd = password.getText().toString();
                    signin(mail,pwd);*/
                    return;
                }
                else{
                    String mail = email.getText().toString();
                    String pwd = password.getText().toString();
                    signin(mail,pwd);
                }
            }
        });
        String Email=email.getText().toString().trim();
        String pass=password.getText().toString().trim();
        if (Email.isEmpty() && pass.isEmpty()){
            //ll.isFocusable();
            //ll.isFocusableInTouchMode();
            //closek();
        }

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closek();
            }
        });
        isNetworkConnectionAvailable();



    }

    private void closek() {

        View view = this.getCurrentFocus();
        if(view!=null ){
           // email.requestFocus();
            InputMethodManager imm =(InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    private boolean validateEmail(){
        String emailInput = email.getText().toString().trim();
        if(emailInput.isEmpty()){
            email.setError("Field Can't be empty");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String passwordInput = password.getText().toString().trim();
        if(passwordInput.isEmpty()){

            password.setError("Field Can't be empty");
            // passwordLayout.passwordToggleEnabled(false);
            // Toast.makeText(getApplicationContext()."Email or password is incorrect")
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }

    private void signin(String mail, String pwd) {
        auth.signInWithEmailAndPassword(mail,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(logIn.this,"Signed in",Toast.LENGTH_LONG).show();
                   startActivity(new Intent(logIn.this,ListRecyclerView.class));
                   //intentGiven();
                }else{
                    user1=FirebaseAuth.getInstance().getCurrentUser();
                    if(user1!=null){
                        boolean emailVerified = user1.isEmailVerified();
                        if(!emailVerified){
                            email.setError("Please enter valid e-mail or password");
                        }
                    }
                    //Toast.makeText(MainActivity.this,"Not Signed in",Toast.LENGTH_LONG).show();
                }
            }
        });
    }




    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
           // Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
           // Log.d("Network","Not Connected");
            return false;
        }
    }
    //TODO
    public void intentGiven(){
       String mail11=email.getText().toString().trim();

       /* boolean p1 = Pattern.matches("17",mail11);
        boolean p2= Pattern.matches("18",mail11);
        boolean p3= Pattern.matches("19",mail11);
        boolean p4= Pattern.matches("[^0-9]]",mail11);*/
        if(!Pattern.matches("17",mail11)){
            startActivity(new Intent(logIn.this,testActivity3.class));
            //finish();
        }
        else if(!Pattern.matches("18",mail11)){
            startActivity(new Intent(logIn.this,testActivity2.class));
            //finish();
        }
        else if(!Pattern.matches("19",mail11)){
            startActivity(new Intent(logIn.this,testActivity.class));
            //finish();
        }
        else if (!Pattern.matches("[^0-9]]",mail11)){
            startActivity(new Intent(logIn.this,ListRecyclerView.class));
            //finish();
        }
    }
}

