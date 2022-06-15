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

/**
 * Main Activity.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- In this Activity the user connects to the application with Firebase Authentication.
 */

public class MainActivity extends AppCompatActivity {
    EditText email1,password1;
    String email01,password01;
    public static String uId, uId2;
    //uId- the user uId from Firebase Authentication. uId2- the uId of a "future seller".
    public static ArrayList<String> g,t,sC1,sS1,s2,c;
    //ArrayLists of all categories for item description.
    //g- gender, t- type, sC1- size clothes, sS1- size shoes, s2- status, c- color.
    public static Item singleItem= new Item(), singleItem2= new Item();
    //singleItem- an item that the user wants to see, his item. singleItem2- an item that the user wants to see, other user item.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email1= (EditText) findViewById(R.id.email1);
        password1= (EditText) findViewById(R.id.password1);

        //Reading from firebase database all categories for item description and save it in ArrayLists.
        g= gender1();
        t= type1();
        sC1= sizeC1();
        sS1= sizeS1();
        s2= status1();
        c= color1();
        
    }

    /**
     * LogIn.
     * Short description- Checks if the user has entered an email and password.
     * If he did not enter, an appropriate message is displayed to the user. Otherwise it checks with Firebase Authentication if there is a user with the entered email and password.
     * If there is no such user, an error message appears. On the other hand, if there is a user, it is transferred to the activity "AllItems".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
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
    /**
     * Update2.
     * Short description- Accepts if the logIn was successful.
     * If it wasn't, an error message is displayed. Otherwise, a message is displayed and the user is moved to the activity "allItems".
     */
    public void update2(boolean b1){
        if (b1) {
            Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
            Intent moveToAllItems1= new Intent(this,AllItems.class);
            startActivity(moveToAllItems1);
        }
        else{
            email1.setText("");
            password1.setText("");
            Toast.makeText(this, "Incorrect mail or password, try again:)", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * NewAccount.
     * Short description- Moves from this activity to the activity "signIn".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void newAccount(View view) {
        Intent moveToSingIn1= new Intent(this,SignIn.class);
        startActivity(moveToSingIn1);
    }
}