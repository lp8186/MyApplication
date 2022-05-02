package com.example.myapplication;

import static com.example.myapplication.FBref.FBDB;
import static com.example.myapplication.FBref.mAuth;
import static com.example.myapplication.FBref.refUsers;
import static com.example.myapplication.Tabels.color1;
import static com.example.myapplication.Tabels.gender1;
import static com.example.myapplication.Tabels.sizeC1;
import static com.example.myapplication.Tabels.sizeS1;
import static com.example.myapplication.Tabels.status1;
import static com.example.myapplication.Tabels.type1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText email1,password1;
    String email01,password01;
    public static String uId, uId2;
    public static ArrayList<String> g,t,sC1,sS1,s2,c;
    public static Item singleItem= new Item(), singleItem2= new Item();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email1= (EditText) findViewById(R.id.email1);
        password1= (EditText) findViewById(R.id.password1);

        g= gender1();
        t= type1();
        sC1= sizeC1();
        sS1= sizeS1();
        s2= status1();
        c= color1();
        
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
                                uId= mAuth.getUid();
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
            Intent temp= new Intent(this,AllItems.class);
            startActivity(temp);
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