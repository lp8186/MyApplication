package com.example.myapplication;

import static com.example.myapplication.FBref.refItems;
import static com.example.myapplication.FBref.refItemsA;
import static com.example.myapplication.FBref.refItemsA2;
import static com.example.myapplication.FBref.refItemsA3;
import static com.example.myapplication.FBref.refItemsD;
import static com.example.myapplication.FBref.storage;
import static com.example.myapplication.FBref.storageItem;
import static com.example.myapplication.FBref.storageUser;
import static com.example.myapplication.MainActivity.singleItem;
import static com.example.myapplication.MainActivity.uId;
import static com.example.myapplication.Tabels.gender;
import static com.example.myapplication.Tabels.sizeC;
import static com.example.myapplication.Tabels.sizeS;
import static com.example.myapplication.Tabels.status;
import static com.example.myapplication.Tabels.type;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

/**
 * Single Item.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- This activity shows information about a selected item.
 */

public class SingleItem extends AppCompatActivity {
    ImageView singleItemPhoto;
    TextView singleItemText;
    String singleItemInformation;

    AlertDialog.Builder adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        singleItemPhoto= (ImageView) findViewById(R.id.singleItemPhoto);
        singleItemText= (TextView) findViewById(R.id.singleItemText);

        singleItemInformation= "Price: "+String.valueOf(singleItem.getPrice());
        singleItemInformation= singleItemInformation+"\n"+"Gender: "+ gender.get(singleItem.getGender());
        singleItemInformation= singleItemInformation+"\n"+"Type: "+ type.get(singleItem.getType());
        singleItemInformation= singleItemInformation+"\n"+"Status: " +status.get(singleItem.getStatus());
        singleItemInformation= singleItemInformation+"\n"+"Color: " +Tabels.color.get(singleItem.getColor());
        if (singleItem.getType()==9){
            singleItemInformation= singleItemInformation+"\n"+"Size: "+ sizeS.get(singleItem.getSize());
        }
        else if (singleItem.getType()!=8){
            singleItemInformation= singleItemInformation+"\n"+"Size: " +sizeC.get(singleItem.getSize());
        }

        singleItemText.setText(singleItemInformation);
        String help2= singleItem.getUserId()+ singleItem.getIdentify();
        storageItem= storage.getReference().child("item").child(help2);
        storageItem.getBytes(1024*1024*10).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                singleItemPhoto.setImageBitmap(bitmap);
            }
        });
    }

    /**
     * Change.
     * Short description- Moves from this activity to the activity "UpdateItem".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void change(View view) {
        Intent moveToUpdateItem1= new Intent(this, UpdateItem.class);
        startActivity(moveToUpdateItem1);
    }

    /**
     * Delete.
     * Short description- Checks if the user really wants to delete this item.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void delete(View view) {
        adb= new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle("Delete Item");
        adb.setMessage("Would you like to delete this item?");
        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete2();
            }
        });
        adb.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog ad= adb.create();
        ad.show();
    }

    /**
     * Delete2.
     * Short description- Moves the item to the "ItemsD" branch in the database and transfers the user to the activity "UserProfile".
     */
    public void delete2(){
        refItems.child(uId+singleItem.getIdentify()).removeValue();

        refItemsA2 = refItemsA.child(String.valueOf(singleItem.getGender()));
        refItemsA3 = refItemsA2.child(String.valueOf(singleItem.getType()));
        refItemsA3.child(uId+singleItem.getIdentify()).removeValue();

        singleItem.setActive(false);
        refItemsD.child(uId+(singleItem.getIdentify())).setValue(singleItem);

        Intent moveToUserProfile2= new Intent(this, UserProfile.class);
        startActivity(moveToUserProfile2);
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