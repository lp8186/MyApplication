package com.example.myapplication;

import static com.example.myapplication.FBref.refItems;
import static com.example.myapplication.FBref.refUsers;
import static com.example.myapplication.FBref.storage;
import static com.example.myapplication.FBref.storageUser;
import static com.example.myapplication.MainActivity.singleItem;
import static com.example.myapplication.MainActivity.singleItem2;
import static com.example.myapplication.MainActivity.uId;
import static com.example.myapplication.MainActivity.uId2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * User Profile 2.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- This activity shows the profile of a selected seller with all the items he uploaded.
 */

public class UserProfile2 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ImageView sellerPicture;
    TextView sellerInfo, userText2;
    ListView lV2;
    User temp;
    Item temp2;

    String sellerInfo2;
    Query query,query2;

    CustomAdapter customadp;
    ArrayList<Item> sellerItems= new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);

        sellerPicture= (ImageView) findViewById(R.id.sellerPicture);
        sellerInfo= (TextView) findViewById(R.id.sellerInfo);
        userText2= (TextView) findViewById(R.id.userText2);
        lV2= (ListView) findViewById(R.id.lV2);

        query=refUsers.orderByChild("userId").equalTo(uId2);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for(DataSnapshot data : dS.getChildren()) {
                    temp=data.getValue(User.class);
                    sellerInfo2= temp.getName()+"\n"+"\n"+
                            "Phone: "+temp.getPhone()+"\n"+
                            "City: "+temp.getCity();
                    userText2.setText(temp.getDescription());
                    sellerInfo.setText(sellerInfo2);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        storageUser= storage.getReference().child("profile").child(uId2);
        storageUser.getBytes(1024*1024*5).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                sellerPicture.setImageBitmap(bitmap);
            }
        });

        query2=refItems.orderByChild("userId").equalTo(uId2);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                sellerItems.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    temp2=data.getValue(Item.class);
                    sellerItems.add(temp2);
                }
                customadp= new CustomAdapter(UserProfile2.this, sellerItems);
                lV2.setAdapter(customadp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        lV2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lV2.setOnItemClickListener(this);
    }


    /**
     * OnItemClick.
     * Short description- Gets the selected item.
     * <p>
     *      AdapterView<?> adapterView
     *      View view
     *      int position
     *      long id
     * @param adapterView- the chosen listView.
     * @param view- the chosen item.
     * @param i- the place of the chosen item.
     * @param l- the chosen line.
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent moveToSingleItem3= new Intent(this,SingleItem2.class);
        singleItem2= sellerItems.get(i);
        startActivity(moveToSingleItem3);
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
        else if(id==R.id.Help){
            temp = new Intent(this, Help.class);
            startActivity(temp);
        }
        return true;
    }
}