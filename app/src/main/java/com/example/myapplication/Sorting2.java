package com.example.myapplication;

import static com.example.myapplication.MainActivity.c;
import static com.example.myapplication.MainActivity.g;
import static com.example.myapplication.MainActivity.s2;
import static com.example.myapplication.MainActivity.sC1;
import static com.example.myapplication.MainActivity.sS1;
import static com.example.myapplication.MainActivity.t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Sorting 2.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- In this activity the user can filter all the items by status, color and size.
 */

public class Sorting2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView sortInfo2;
    Spinner spinnerS22, spinnerC2, spinnerS12;
    //spinnerS22- status, spinnerC2- color, spinnerS12- size.

    Switch switch1;

    String info2="Sorting:";
    int one=0,two=0,three=0,four=0,five=555;

    //In order to make sure the user chose gender and type.
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences temp= getSharedPreferences("SortingInfo",MODE_PRIVATE);
        if((temp.getInt("gender",1000)==0)||(temp.getInt("type",1000)==0)){
            Intent backToSorting1= new Intent(this, Sorting.class);
            startActivity(backToSorting1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting2);

        sortInfo2= (TextView) findViewById(R.id.sortInfo2);
        spinnerS22= (Spinner) findViewById(R.id.spinnerS22);
        spinnerC2= (Spinner) findViewById(R.id.spinnerC2);
        spinnerS12= (Spinner) findViewById(R.id.spinnerS12);
        switch1= (Switch) findViewById(R.id.switch1);

        ArrayAdapter<String> adp1= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,s2);
        spinnerS22.setAdapter(adp1);

        ArrayAdapter<String> adp2= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,c);
        spinnerC2.setAdapter(adp2);

        SharedPreferences temp2=getSharedPreferences("SortingInfo",MODE_PRIVATE);
            one =temp2.getInt("gender", 1000);
            two = temp2.getInt("type", 1000);
            updateTextView2();


        if ((two==0)||(two==7)||(two==8)){
            spinnerS12.setVisibility(View.INVISIBLE);
            switch1.setVisibility(View.INVISIBLE);
        }
        else if (two==9){
            ArrayAdapter<String> adp3= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sS1);
            spinnerS12.setAdapter(adp3);
                switch1.setChecked(true);
                switch1.setText("With Size Sorting");
                switch1.setVisibility(View.VISIBLE);
                spinnerS12.setVisibility(View.VISIBLE);
        }
        else{
            ArrayAdapter<String> adp3= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sC1);
            spinnerS12.setAdapter(adp3);
                switch1.setChecked(true);
                switch1.setText("With Size Sorting");
                switch1.setVisibility(View.VISIBLE);
                spinnerS12.setVisibility(View.VISIBLE);
        }
        spinnerC2.setOnItemSelectedListener(this);
        spinnerS12.setOnItemSelectedListener(this);
        spinnerS22.setOnItemSelectedListener(this);
    }

    /**
     * OnItemSelected.
     * Short description- Gets the selected status, color and size.
     * <p>
     *      AdapterView<?> adapterView
     *      View view
     *      int i
     *      long l
     * @param adapterView- the chosen spinner.
     * @param view- the chosen item.
     * @param i- the place of the chosen item.
     * @param l- the chosen line.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView==spinnerS22){
            three=i;
            updateTextView2();
        }
        if (adapterView==spinnerC2){
            four=i;
            updateTextView2();
        }
        if (adapterView==spinnerS12){
            five=i;
            updateTextView2();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Select.
     * Short description- Checks if the user wants to sort according to size.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void select(View view) {
        if(switch1.isChecked()){
            spinnerS12.setVisibility(View.VISIBLE);
            five=0;
            updateTextView2();
            switch1.setText("With Size Sorting");
        }
        else {
            spinnerS12.setVisibility(View.INVISIBLE);
            five=555;
            updateTextView2();
            switch1.setText("Without Size Sorting");
        }
    }

    /**
     * StartOver2.
     * Short description- Cleans sort info and moves from this activity to the activity "Sorting".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void startOver2(View view) {
        SharedPreferences temp3=getSharedPreferences("SortingInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=temp3.edit();
        editor.putInt("gender",0);
        editor.putInt("type",0);
        editor.putInt("status",0);
        editor.putInt("color",0);
        editor.putInt("size",555);
        editor.commit();
        Intent backToSorting2= new Intent(this, Sorting.class);
        startActivity(backToSorting2);
    }

    /**
     * Done.
     * Short description- Updates the sort information in Shared Preferences and moves from this activity to the activity "AllItems".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void done(View view) {
        SharedPreferences temp4=getSharedPreferences("SortingInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=temp4.edit();
        editor.putInt("status",three);
        editor.putInt("color",four);
        editor.putInt("size",five);
        editor.commit();
        Intent moveToAllItems3= new Intent(this, AllItems.class);
        startActivity(moveToAllItems3);
    }

    /**
     * UpdateTextView2.
     * Short description- Updates and displays the sort information.
     */
    public void updateTextView2(){
        info2= "Sorting:";
        if (one!=0)
            info2= info2+"\n"+g.get(one);
        if (two!=0)
            info2= info2+"\n"+t.get(two);
        if (three!=0)
            info2= info2+"\n"+s2.get(three);
        if (four!=0)
            info2= info2+"\n"+c.get(four);
        if (five!=555){
            if ((two==0)||(two==7)||(two==8))
                five=555;
            else if (two==9)
                info2= info2+"\n"+sS1.get(five);
            else
                info2=info2+"\n"+sC1.get(five);
        }
        sortInfo2.setText(info2);
    }
}