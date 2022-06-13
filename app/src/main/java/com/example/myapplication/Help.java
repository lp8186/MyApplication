package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Help.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- Credits.
 */

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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
        else if (id==R.id.Sorting) {
            temp = new Intent(this, Sorting.class);
            startActivity(temp);
        }
        else if (id==R.id.NewItem) {
            temp = new Intent(this, NewItem.class);
            startActivity(temp);
        }
        return true;
    }
}