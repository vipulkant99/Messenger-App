package com.example.bca_messenger;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class pass extends AppCompatActivity {
    private EditText current;
    private EditText newpsswd;
    private EditText retype;
    private Button save;
    private logIn log = new logIn();
    private FirebaseUser use;
    private TextView text;
    private TextView text1;
    private TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        current=(EditText)findViewById(R.id.current);
        newpsswd=(EditText)findViewById(R.id.newpsswd);
        retype=(EditText)findViewById(R.id.retype);
        save=(Button)findViewById(R.id.save);
        text=(TextView)findViewById(R.id.text);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        String old10 = log.password.getText().toString();
        text.setText(old10);
        use= FirebaseAuth.getInstance().getCurrentUser();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd=current.getText().toString();
                String old1 = log.old;
                String newp=newpsswd.getText().toString();
                String re=retype.getText().toString();
                if (old1.matches(pwd) && newp.matches(re)){
                    use.updatePassword(newp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(pass.this,"Password changed",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(pass.this,"Password error",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


}
