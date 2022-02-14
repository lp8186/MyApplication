package com.example.myapplication;

import static com.example.myapplication.FBref.FBDB;
import static com.example.myapplication.FBref.mAuth;
import static com.example.myapplication.FBref.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText email1,password1;
    String email01,password01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email1= (EditText) findViewById(R.id.email1);
        password1= (EditText) findViewById(R.id.password1);
        
    }

    public void logIn(View view) {
        email01= email1.getText().toString();
        password01= password1.getText().toString();
        if (email01.isEmpty()){
            Toast.makeText(this, "Enter your mail", Toast.LENGTH_SHORT).show();
        }
        else if (password01.isEmpty()) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email01,password01)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                update2(true);
                            } else {
                                update2(false);
                            }
                        }
                    });
        }
    }
    public void update2(boolean b1){
        if (b1) {
            Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
        }
        else{
            email1.setText("");
            password1.setText("");
            Toast.makeText(this, "We have a problem", Toast.LENGTH_SHORT).show();
        }
    }

    public void newAccount(View view) {
        Intent temp= new Intent(this,SignIn.class);
        startActivity(temp);
    }
}