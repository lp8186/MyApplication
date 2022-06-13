package com.example.myapplication;


import static com.example.myapplication.FBref.refItems;
import static com.example.myapplication.FBref.refUsers;
import static com.example.myapplication.FBref.storage;
import static com.example.myapplication.FBref.storageItem;
import static com.example.myapplication.FBref.storageUser;
import static com.example.myapplication.MainActivity.singleItem;
import static com.example.myapplication.MainActivity.uId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * User Profile.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- This activity shows the user's profile with all the items he uploaded.
 */

public class UserProfile extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ImageView userPicture;
    TextView userInfo, userText;
    ListView lV;
    User temp;
    Item temp2;

    String userInfo2;
    Query query,query2;

    CustomAdapter customadp;
    ArrayList<Item> userItems= new ArrayList<Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userPicture= (ImageView) findViewById(R.id.userPicture);
        userInfo= (TextView) findViewById(R.id.userInfo);
        userText= (TextView) findViewById(R.id.userText);
        lV= (ListView) findViewById(R.id.lV);


        query=refUsers.orderByChild("userId").equalTo(uId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for(DataSnapshot data : dS.getChildren()) {
                    temp=data.getValue(User.class);
                    userInfo2= temp.getName()+"\n"+"\n"+
                            "Phone: "+temp.getPhone()+"\n"+
                            "City: "+temp.getCity();
                    userText.setText(temp.getDescription());
                    userInfo.setText(userInfo2);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        storageUser= storage.getReference().child("profile").child(uId);
        storageUser.getBytes(1024*1024*5).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                userPicture.setImageBitmap(bitmap);
            }
        });

        query2=refItems.orderByChild("userId").equalTo(uId);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                userItems.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    temp2=data.getValue(Item.class);
                    userItems.add(temp2);
                }
                customadp= new CustomAdapter(UserProfile.this, userItems);
                lV.setAdapter(customadp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        lV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lV.setOnItemClickListener(this);


    }

    /**
     * OnItemClick.
     * Short description- Gets the selected item.
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
        Intent move= new Intent(this,SingleItem.class);
        singleItem= userItems.get(i);
        startActivity(move);
    }

    /**
     * MoveToNewItem.
     * Short description- Moves from this activity to the activity "NewItem".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void moveToNewItem(View view) {
        Intent move2= new Intent(this,NewItem.class);
        startActivity(move2);
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