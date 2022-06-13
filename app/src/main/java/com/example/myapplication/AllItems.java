package com.example.myapplication;

import static com.example.myapplication.FBref.refItems;
import static com.example.myapplication.FBref.refItemsA;
import static com.example.myapplication.FBref.refItemsA2;
import static com.example.myapplication.FBref.refItemsA3;
import static com.example.myapplication.MainActivity.c;
import static com.example.myapplication.MainActivity.g;
import static com.example.myapplication.MainActivity.s2;
import static com.example.myapplication.MainActivity.sC1;
import static com.example.myapplication.MainActivity.sS1;
import static com.example.myapplication.MainActivity.singleItem;
import static com.example.myapplication.MainActivity.singleItem2;
import static com.example.myapplication.MainActivity.t;
import static com.example.myapplication.MainActivity.uId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * All Items.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- This activity shows all the items that have been uploaded to the app so far.
 */

public class AllItems extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    ListView itemsList;
    TextView sortInfo3;
    Spinner sort;
    Item temp3;
    ArrayList<Item> items= new ArrayList<>();
    ArrayList<Item> items2= new ArrayList<>();
    String [] sort2= {"order by place","order by price","order by size"};
    CustomAdapter customadp;

    Query query;
    int one=0, two=0, three=0, four=0, five=555;
    String info3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        itemsList= (ListView) findViewById(R.id.itemsList);
        sortInfo3= (TextView) findViewById(R.id.sortInfo3);
        sort= (Spinner) findViewById(R.id.sort);

        ArrayAdapter<String> adpSort= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sort2);
        sort.setAdapter(adpSort);
        sort.setOnItemSelectedListener(this);

        itemsList.setOnItemClickListener(this);

        SharedPreferences temp=getSharedPreferences("SortingInfo",MODE_PRIVATE);
        if(temp.getInt("gender",1000)==0){
            sortInfo3.setText("All The Items We Have");
            refItems.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dS) {
                    items.clear();
                    for(DataSnapshot data : dS.getChildren()) {
                        temp3=data.getValue(Item.class);
                        items.add(temp3);
                    }
                    customadp= new CustomAdapter(AllItems.this, items);
                    itemsList.setAdapter(customadp);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        else {
            one = temp.getInt("gender", 1000);
            two = temp.getInt("type", 1000);
            three = temp.getInt("status", 1000);
            four = temp.getInt("color", 1000);
            five = temp.getInt("size", 1000);
            updateTextView3();
            refItemsA2 = refItemsA.child(String.valueOf(one));
            refItemsA3 = refItemsA2.child(String.valueOf(two));
            //בוודאות יש סינון של מין וסוג פריט
            if ((three == 0) && (four == 0) && (five == 555)) {
                refItemsA3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dS) {
                        items.clear();
                        for (DataSnapshot data : dS.getChildren()) {
                            temp3 = data.getValue(Item.class);
                            items.add(temp3);
                        }
                        customadp = new CustomAdapter(AllItems.this, items);
                        itemsList.setAdapter(customadp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            else if (three != 0) {
                query = refItemsA3.orderByChild("status").equalTo(three);
                if ((four == 0) && (five == 555)) {
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            items.clear();
                            for (DataSnapshot data : dS.getChildren()) {
                                temp3 = data.getValue(Item.class);
                                items.add(temp3);
                            }
                            customadp = new CustomAdapter(AllItems.this, items);
                            itemsList.setAdapter(customadp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                } else if ((four != 0) && (five == 555)) {
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            items.clear();
                            for (DataSnapshot data : dS.getChildren()) {
                                temp3 = data.getValue(Item.class);
                                if ((temp3.getColor() == four)) {
                                    items.add(temp3);
                                }
                            }
                            customadp = new CustomAdapter(AllItems.this, items);
                            itemsList.setAdapter(customadp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                } else if ((four == 0) && (five != 555)) {
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            items.clear();
                            for (DataSnapshot data : dS.getChildren()) {
                                temp3 = data.getValue(Item.class);
                                if ((temp3.getSize() == five)) {
                                    items.add(temp3);
                                }
                            }
                            customadp = new CustomAdapter(AllItems.this, items);
                            itemsList.setAdapter(customadp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                } else {
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            items.clear();
                            for (DataSnapshot data : dS.getChildren()) {
                                temp3 = data.getValue(Item.class);
                                if ((temp3.getColor() == four) && (temp3.getSize() == five)) {
                                    items.add(temp3);
                                }
                            }
                            customadp = new CustomAdapter(AllItems.this, items);
                            itemsList.setAdapter(customadp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            } else if (four != 0) {
                query = refItemsA3.orderByChild("color").equalTo(four);
                if (five == 555) {
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            items.clear();
                            for (DataSnapshot data : dS.getChildren()) {
                                temp3 = data.getValue(Item.class);
                                items.add(temp3);

                            }
                            customadp = new CustomAdapter(AllItems.this, items);
                            itemsList.setAdapter(customadp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                } else {
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            items.clear();
                            for (DataSnapshot data : dS.getChildren()) {
                                temp3 = data.getValue(Item.class);
                                if ((temp3.getSize() == five)) {
                                    items.add(temp3);
                                }
                            }
                            customadp = new CustomAdapter(AllItems.this, items);
                            itemsList.setAdapter(customadp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            } else {
                query = refItemsA3.orderByChild("size").equalTo(five);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dS) {
                        items.clear();
                        for (DataSnapshot data : dS.getChildren()) {
                            temp3 = data.getValue(Item.class);
                            items.add(temp3);
                        }
                        customadp = new CustomAdapter(AllItems.this, items);
                        itemsList.setAdapter(customadp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }


        }
        items2= items;
        //הוספה
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
        Intent move;
        if (items.get(i).getUserId().equals(uId)){
            move= new Intent(this,SingleItem.class);
            singleItem= items.get(i);
        }
        else{
            move= new Intent(this,SingleItem2.class);
            singleItem2= items.get(i);
        }
        startActivity(move);
        /*Intent move= new Intent(this,SingleItem2.class);
        singleItem2= items.get(i);
        startActivity(move);*/
    }

    /**
     * UpdateTextView3.
     * Short description- Updates and displays the sort information.
     * If the user has not sorted the items, the text that will be displayed to the user is "All the Items we have"
     */
    public void updateTextView3(){
        info3= "Sorting:";
        if (one!=0)
            info3= info3+"\n"+g.get(one);
        if (two!=0)
            info3= info3+"\n"+t.get(two);
        if (three!=0)
            info3= info3+"\n"+s2.get(three);
        if (four!=0)
            info3= info3+"\n"+c.get(four);
        Toast.makeText(AllItems.this, String.valueOf(five), Toast.LENGTH_SHORT).show();
        if (five!=555){
            if ((two==0)||(two==7)||(two==8))
                five=555;
            else if (two==9)
                info3= info3+"\n"+sS1.get(five);
            else
                info3=info3+"\n"+sC1.get(five);
        }
        sortInfo3.setText(info3);
    }

    /**
     * OnItemSelected.
     * Short description- Checks which sorting option the user has selected and displays the items in the desired order.
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
        if (i==0){
            items= items2;
            customadp = new CustomAdapter(AllItems.this, items);
            itemsList.setAdapter(customadp);
        }
        else if (i==1){
            if (one==0){
                query= refItems.orderByChild("price");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dS) {
                        items.clear();
                        for(DataSnapshot data : dS.getChildren()) {
                            temp3=data.getValue(Item.class);
                            items.add(temp3);
                        }
                        customadp= new CustomAdapter(AllItems.this, items);
                        itemsList.setAdapter(customadp);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            else{
                query= refItemsA3.orderByChild("price");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dS) {
                        items.clear();
                        for (DataSnapshot data : dS.getChildren()) {
                            temp3 = data.getValue(Item.class);
                            if (((temp3.getStatus()==three)||(three==0))&&((temp3.getColor()==four)||(four==0))&&((temp3.getSize()==five)||(five==555)))
                                items.add(temp3);
                        }
                        customadp = new CustomAdapter(AllItems.this, items);
                        itemsList.setAdapter(customadp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }
        else{
            if (one==0){
                query= refItems.orderByChild("size");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dS) {
                        items.clear();
                        for(DataSnapshot data : dS.getChildren()) {
                            temp3=data.getValue(Item.class);
                            items.add(temp3);
                        }
                        customadp= new CustomAdapter(AllItems.this, items);
                        itemsList.setAdapter(customadp);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            else{
                if (five!=555){
                    Toast.makeText(this, "You Already have size sorting:)", Toast.LENGTH_SHORT).show();
                }
                else {
                    query = refItemsA3.orderByChild("size");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            items.clear();
                            for (DataSnapshot data : dS.getChildren()) {
                                temp3 = data.getValue(Item.class);
                                if (((temp3.getStatus() == three) || (three == 0)) && ((temp3.getColor() == four) || (four == 0)))
                                    items.add(temp3);
                            }
                            customadp = new CustomAdapter(AllItems.this, items);
                            itemsList.setAdapter(customadp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * MoveToSorting.
     * Short description- Moves from this activity to the activity "Sorting".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void moveToSorting(View view) {
        Intent move3= new Intent(this,Sorting.class);
        startActivity(move3);
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
        if (id==R.id.Sorting) {
            temp = new Intent(this, Sorting.class);
            startActivity(temp);
        }
        else if(id==R.id.NewItem){
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