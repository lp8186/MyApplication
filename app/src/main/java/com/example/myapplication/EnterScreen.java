package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



/**
 * Enter Screen.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- The first screen you see when you enter to the app, this activity shows the app logo for three seconds.
 */

public class EnterScreen extends AppCompatActivity {

    private static long SPLASH_TIME_OUT= 2000;


    /**
     * Run.
     * Short description- Moves without the user intervention, from the "EnterScreen" activity to the "MainActivity" activity after three seconds from the moment the app opens.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent starting= new Intent(EnterScreen.this,MainActivity.class);
                startActivity(starting);
                finish();
            }
        },SPLASH_TIME_OUT);


    }

}