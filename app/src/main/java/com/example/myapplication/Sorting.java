package com.example.myapplication;

import static com.example.myapplication.MainActivity.c;
import static com.example.myapplication.MainActivity.g;
import static com.example.myapplication.MainActivity.s2;
import static com.example.myapplication.MainActivity.sC1;
import static com.example.myapplication.MainActivity.sS1;
import static com.example.myapplication.MainActivity.t;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Sorting.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- In this activity the user can filter all the items by gender and type.
 */

public class Sorting extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView sortInfo;
    ListView options;

    String info="Sorting:";
    int one=0,two=0,three=0,four=0,five=555;
    boolean check;

    AlertDialog.Builder adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        sortInfo= (TextView) findViewById(R.id.sortInfo);
        options= (ListView) findViewById(R.id.options);

        ArrayAdapter<String> adp= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,g);
        options.setAdapter(adp);

        check=false;

        SharedPreferences temp=getSharedPreferences("SortingInfo",MODE_PRIVATE);
        if(temp.getInt("gender",1000)==0){
            sortInfo.setText(info);
        }
        else {
            adb= new AlertDialog.Builder(this);
            adb.setCancelable(false);
            adb.setTitle("Sorting");
            adb.setMessage("Would you like to start new Sorting?");
            adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sortInfo.setText(info);
                    //SharedPreferences temp=getSharedPreferences("SortingInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor=temp.edit();
                    editor.putInt("gender",0);
                    editor.putInt("type",0);
                    editor.putInt("status",0);
                    editor.putInt("color",0);
                    editor.putInt("size",555);
                    editor.commit();
                }
            });
            adb.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent backToAllItems1= new Intent(Sorting.this, AllItems.class);
                    startActivity(backToAllItems1);
                }
            });
            AlertDialog ad= adb.create();
            ad.show();
        }

        options.setOnItemClickListener(this);
        options.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    /**
     * OnItemClick.
     * Short description- Gets the selected gender and type.
     * After the user chooses the item's type, it transfers to the activity "Sorting2".
     * <p>
     *      AdapterView<?> adapterView
     *      View view
     *      int i
     *      long l
     * @param adapterView- the chosen listView.
     * @param view- the chosen item.
     * @param i- the place of the chosen item.
     * @param l- the chosen line.
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (!(check)){
            if (i!=0){
                //this choice will be saved on Shared Preferences after the user selects a type.
                one=i;
                check=true;
                ArrayAdapter<String> adp2= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,t);
                options.setAdapter(adp2);
            }
        }
        else if (check){
            if (i!=0){
                two= i;
                updateSortInfo2();
                Intent nextSorting= new Intent(this, Sorting2.class);
                startActivity(nextSorting);
            }

        }
    }

    /**
     * UpdateSortInfo2.
     * Short description- Updates the sort information in Shared Preferences.
     */
    public void updateSortInfo2(){
        SharedPreferences temp2=getSharedPreferences("SortingInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=temp2.edit();
        editor.putInt("gender",one);
        editor.putInt("type",two);
        editor.commit();
        updateTextView();
    }

    /**
     * StartOver.
     * Short description- Cleans sort info.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void startOver(View view) {
        info= "Sorting:";
        sortInfo.setText(info);
        SharedPreferences temp3=getSharedPreferences("SortingInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=temp3.edit();
        editor.putInt("gender",0);
        editor.putInt("type",0);
        editor.putInt("status",0);
        editor.putInt("color",0);
        editor.putInt("size",555);
        editor.commit();

        ArrayAdapter<String> adp= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,g);
        options.setAdapter(adp);

        check=false;

        one=0; two=0; three=0; four=0; five=555;
    }

    /**
     * UpdateTextView.
     * Short description- Updates and displays the sort information.
     */
    public void updateTextView(){
        info= "Sorting:";
        if (one!=0)
            info= info+"\n"+g.get(one);
        if (two!=0)
            info= info+"\n"+t.get(two);
        if (three!=0)
            info= info+"\n"+s2.get(three);
        if (four!=0)
            info= info+"\n"+c.get(four);
        if (five!=555){
            if ((two==0)||(two==7)||(two==8))
                five=555;
            else if (two==9)
                info= info+"\n"+sS1.get(five);
            else
                info=info+"\n"+sC1.get(five);
        }
        sortInfo.setText(info);

    }

    /**
     * OnCreateOptionsMenu.
     * Short descriptions- Calls the options menu.
     * <p>
     *    Menu menu
     * @param menu- the menu.
     * @return true if it worked.
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * OnOptionsItemSelected.
     * Short description- Moves from this activity to other activity according to the selected item.
     * <p>
     *     MenuItem item
     * @param item- the selected item.
     * @return true if it worked.
     */
    public boolean onOptionsItemSelected(MenuItem item){
        Intent temp;
        int id= item.getItemId();
        if(id==R.id.AllItems){
            temp = new Intent(this, AllItems.class);
            startActivity(temp);
        }
        else if (id==R.id.NewItem) {
            temp = new Intent(this, NewItem.class);
            startActivity(temp);
        }
        else if(id==R.id.UserProfile){
            temp = new Intent(this, UserProfile.class);
            startActivity(temp);
        }
        else if(id==R.id.Help){
            temp = new Intent(this, Help.class);
            startActivity(temp);
        }
        return true;
    }
}