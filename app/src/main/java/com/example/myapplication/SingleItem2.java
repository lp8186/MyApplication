package com.example.myapplication;

import static com.example.myapplication.FBref.refUsers;
import static com.example.myapplication.FBref.storage;
import static com.example.myapplication.FBref.storageItem;
import static com.example.myapplication.MainActivity.singleItem;
import static com.example.myapplication.MainActivity.singleItem2;
import static com.example.myapplication.MainActivity.uId2;
import static com.example.myapplication.Tabels.gender;
import static com.example.myapplication.Tabels.sizeC;
import static com.example.myapplication.Tabels.sizeS;
import static com.example.myapplication.Tabels.status;
import static com.example.myapplication.Tabels.type;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Single Item 2.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- This activity shows information about a selected item.
 */

public class SingleItem2 extends AppCompatActivity {
    ImageView singleItemPhoto2;
    TextView singleItemText2;
    String singleItemInformation2;

    Query query;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String textMessage, phoneMessage;
    AlertDialog.Builder adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item2);

        singleItemPhoto2= (ImageView) findViewById(R.id.singleItemPhoto2);
        singleItemText2= (TextView) findViewById(R.id.singleItemText2);

        uId2= singleItem2.getUserId();
        query=refUsers.orderByChild("userId").equalTo(uId2);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for(DataSnapshot data : dS.getChildren()) {
                    phoneMessage=data.getValue(User.class).getPhone();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        singleItemInformation2= "Price: "+String.valueOf(singleItem2.getPrice());
        singleItemInformation2= singleItemInformation2+"\n"+"Gender: " +gender.get(singleItem2.getGender());
        singleItemInformation2= singleItemInformation2+"\n"+"Type: " +type.get(singleItem2.getType());
        singleItemInformation2= singleItemInformation2+"\n"+"Status: " +status.get(singleItem2.getStatus());
        singleItemInformation2= singleItemInformation2+"\n"+"Color: " +Tabels.color.get(singleItem2.getColor());
        if (singleItem2.getType()==9){
            singleItemInformation2= singleItemInformation2+"\n"+"Size: " +sizeS.get(singleItem2.getSize());
        }
        else if ((singleItem2.getType()!=8)&&(singleItem2.getType()!=9)){
            singleItemInformation2= singleItemInformation2+"\n"+"Size: " +sizeC.get(singleItem2.getSize());
        }

        singleItemText2.setText(singleItemInformation2);
        String help2= singleItem2.getUserId()+ singleItem2.getIdentify();
        storageItem= storage.getReference().child("item").child(help2);
        storageItem.getBytes(1024*1024*10).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                singleItemPhoto2.setImageBitmap(bitmap);
            }
        });
    }

    /**
     * MoveToProfile.
     * Short description- Moves from this activity to the activity "UserProfile2".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void moveToProfile(View view) {
        Intent moveToUserProfile3= new Intent(this,UserProfile2.class);
        startActivity(moveToUserProfile3);
    }

    /**
     * SendSMS.
     * Short description- Creates an alert dialog and the user can enter inside the text he wants to send in a SMS to the user who sells the item.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void sendSMS(View view) {
        adb= new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle("Send SMS");
        final EditText message= new EditText(this);
        message.setText("I want to buy your "+singleItem2.getItemDescription());
        message.setHint("write a message");
        adb.setView(message);
        adb.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textMessage= message.getText().toString();
                if (textMessage.isEmpty()){
                    Toast.makeText(getApplicationContext(), "You need to write something", Toast.LENGTH_LONG).show();
                }
                else {
                    sendSMSmessage(textMessage);
                }
            }
        });
        adb.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog ad= adb.create();
        ad.show();
    }

    protected void sendSMSmessage(String textMessage){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneMessage, null, textMessage, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    /**
     * MoveToWhatsapp.
     * Short description- Moves from this activity to the app "Whatsapp".
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void moveToWhatsapp(View view) {
        Intent moveToWhatsapp1= new Intent(Intent.ACTION_VIEW);
        moveToWhatsapp1.setData(Uri.parse("https://wa.me/"+phoneMessage));
        startActivity(moveToWhatsapp1);
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