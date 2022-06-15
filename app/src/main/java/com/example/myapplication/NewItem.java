package com.example.myapplication;

import static com.example.myapplication.FBref.refItems;
import static com.example.myapplication.FBref.refItemsA;
import static com.example.myapplication.FBref.refUsers;
import static com.example.myapplication.FBref.storageReference;
import static com.example.myapplication.MainActivity.c;
import static com.example.myapplication.MainActivity.g;
import static com.example.myapplication.MainActivity.s2;
import static com.example.myapplication.MainActivity.sC1;
import static com.example.myapplication.MainActivity.sS1;
import static com.example.myapplication.MainActivity.t;
import static com.example.myapplication.MainActivity.uId;
import static com.example.myapplication.MainActivity.uId2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * New Item.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- In this activity the user adds a new Item for selling.
 */

public class NewItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerG, spinnerT, spinnerS1, spinnerS2,spinnerC;
    int g2=0,t2=0,s12=555,s22=0,c2=0;
    //g2- gender, t2- type, s12- size, s22- status, c2- color.

    ImageView itemPhoto;
    EditText price, itemDescription;
    String price02,itemPhoto02,itemDescription02;
    int price002, itemIdentify=0;

    AlertDialog.Builder photo2;
    boolean checkItemPhoto=false;

    private final int PICK_IMAGE_REQUEST = 22, CAMERA_REQUEST=24;
    Uri filePath;

    Item newItem;

    Query query;

    User tempUser= new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        itemPhoto= (ImageView) findViewById(R.id.itemPhoto);
        itemDescription= (EditText) findViewById(R.id.itemDescription);
        price= (EditText) findViewById(R.id.price);

        spinnerG= (Spinner) findViewById(R.id.spinnerG);
        spinnerT= (Spinner) findViewById(R.id.spinnerT);
        spinnerS1= (Spinner) findViewById(R.id.spinnerS1);
        spinnerS2= (Spinner) findViewById(R.id.spinnerS2);
        spinnerC= (Spinner) findViewById(R.id.spinnerC);

        spinnerS1.setVisibility(View.INVISIBLE);
        //only when you need an item's size, it will be visible.

        spinnerG.setOnItemSelectedListener(this);
        spinnerT.setOnItemSelectedListener(this);
        spinnerS2.setOnItemSelectedListener(this);
        spinnerC.setOnItemSelectedListener(this);
        spinnerS1.setOnItemSelectedListener(this);

        ArrayAdapter<String> adp1= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, g);
        spinnerG.setAdapter(adp1);

        ArrayAdapter<String> adp2= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,t);
        spinnerT.setAdapter(adp2);

        ArrayAdapter<String> adp3= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,s2);
        spinnerS2.setAdapter(adp3);

        ArrayAdapter<String> adp4= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,c);
        spinnerC.setAdapter(adp4);

        checkItemIdentify();
    }

    /**
     * OnItemSelected.
     * Short description- Checks and saves the user's selections for the description of the item.
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
        if (adapterView==spinnerG){
            g2= i;
        }
        if (adapterView==spinnerT){
            t2= i;
            if ((i==0)||(i==7)||(i==8)){
                spinnerS1.setVisibility(View.INVISIBLE);
                s12=555;
            }
            else if (i==9){
                spinnerS1.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adp5= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sS1);
                spinnerS1.setAdapter(adp5);
                s12=0;
            }
            else{
                spinnerS1.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adp5= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sC1);
                spinnerS1.setAdapter(adp5);
                s12=0;
            }
        }
        if (adapterView==spinnerS1){
            s12=i;
        }
        if (adapterView==spinnerS2){
            s22=i;
        }
        if (adapterView==spinnerC){
            c2=i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * AddPhoto2.
     * Short description- Checks where the user wants to upload the image from- Camera or Gallery.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void addPhoto2(View view) {
        photo2= new AlertDialog.Builder(this);
        photo2.setCancelable(false);
        photo2.setTitle("Add photo");
        photo2.setMessage("From where would you like to upload your photo?");
        photo2.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                camera();
            }
        });
        photo2.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gallery();
            }
        });
        AlertDialog photo02= photo2.create();
        photo02.show();
    }

    /**
     * Gallery.
     * Short description- Moves from this activity to Gallery.
     */
    public void gallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here"), PICK_IMAGE_REQUEST);
    }

    /**
     * Camera.
     * Short description- Moves from this activity to Camera.
     */
    public void camera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }
    }

    /**
     * CreateImageFile.
     * Short description- Turns the image taken through the camera into a file in order to get the highest quality image possible.
     * @return the file it created
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        filePath = Uri.fromFile(image);
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap = Bitmap.createScaledBitmap(bitmap,360,480,true);
                itemPhoto.setImageBitmap(bitmap);
                checkItemPhoto=true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            itemPhoto.setImageURI(filePath);
            checkItemPhoto=true;
        }
    }

    /**
     * CreateNewItem.
     * Short description- Checks if the user has filled in all the required fields.
     * If the user has not filled in, a message is displayed to him.
     * Otherwise it uploads the new item that the user wants to post for sale to the database under the "Items" branch and under the "ItemsA" branch.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void createNewItem(View view) {
        if (!(checkItemPhoto)) {
            Toast.makeText(this, "picture your item", Toast.LENGTH_SHORT).show();
        } else {
            itemPhoto02= filePath.getPath();
            if (g2 == 0) {
                Toast.makeText(this, "Enter gender", Toast.LENGTH_SHORT).show();
            } else if (t2 == 0) {
                Toast.makeText(this, "Enter the item's type", Toast.LENGTH_SHORT).show();
            } else if (s22 == 0) {
                Toast.makeText(this, "Enter the item's status", Toast.LENGTH_SHORT).show();
            } else if (c2 == 0) {
                Toast.makeText(this, "Enter the item's color", Toast.LENGTH_SHORT).show();
            }  else {
                price02 = price.getText().toString();
                if (price02.isEmpty()) {
                    Toast.makeText(this, "Enter the price", Toast.LENGTH_SHORT).show();
                } else {
                    itemDescription02 = itemDescription.getText().toString();
                    price002 = Integer.parseInt(price02);
                    uploadProfile();
                    newItem = new Item(uId,true, itemIdentify, g2,t2 ,s22, c2, s12, price002, itemDescription02, itemPhoto02);
                    refItemsA.child(String.valueOf(g2)).child(String.valueOf(t2)).child(uId+itemIdentify).setValue(newItem);
                    refItems.child(uId+itemIdentify).setValue(newItem);
                    updateSortInfo(itemIdentify);
                    Intent moveToUserProfile1= new Intent(this, UserProfile.class);
                    startActivity(moveToUserProfile1);
                }

            }
        }
    }

    /**
     * UploadProfile.
     * Short description- Uploads the user's item image to Firebase Storage.
     */
    private void uploadProfile() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = storageReference.child("item/"+uId+itemIdentify);
            ref.putFile(filePath).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(NewItem.this, "Item Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(NewItem.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }

    /**
     * CheckItemIdentify.
     * Short description- Checks in the database what is the number of the new item that the user wants to upload.
     */
    private void checkItemIdentify(){
        query=refUsers.orderByChild("userId").equalTo(uId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for(DataSnapshot data : dS.getChildren()) {
                    tempUser=data.getValue(User.class);
                    itemIdentify= tempUser.getItemsNum();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * UpdateSortInfo.
     * Short description- Updates the new number of items the user has uploaded so far in the database.
     * <p>
     *      int num
     * @param num- the old num of the user items.
     */
    private void updateSortInfo(int num){
        int temp= num+1;
        tempUser.setItemsNum(temp);
        refUsers.child(uId).setValue(tempUser);
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