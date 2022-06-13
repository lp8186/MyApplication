package com.example.myapplication;

import static com.example.myapplication.FBref.refItems;
import static com.example.myapplication.FBref.refItemsA;
import static com.example.myapplication.FBref.storage;
import static com.example.myapplication.FBref.storageItem;
import static com.example.myapplication.FBref.storageReference;
import static com.example.myapplication.MainActivity.c;
import static com.example.myapplication.MainActivity.g;
import static com.example.myapplication.MainActivity.s2;
import static com.example.myapplication.MainActivity.sC1;
import static com.example.myapplication.MainActivity.sS1;
import static com.example.myapplication.MainActivity.singleItem;
import static com.example.myapplication.MainActivity.t;
import static com.example.myapplication.MainActivity.uId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Update Item.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- In this activity the user can update information about item.
 */

public class UpdateItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerG3, spinnerT3, spinnerS13, spinnerS23,spinnerC3;
    int g3=0,t3=0,s13=555,s23=0,c3=0;

    ImageView itemPhoto3;
    EditText price3, itemDescription3;
    String price03,itemPhoto03,itemDescription03;
    int price003, itemIdentify3;

    AlertDialog.Builder photo3, adb;
    boolean changePhoto=false;

    private final int PICK_IMAGE_REQUEST = 22, CAMERA_REQUEST=24;
    Uri filePath;

    Item newItem, oldItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        itemPhoto3= (ImageView) findViewById(R.id.itemPhoto3);
        itemDescription3= (EditText) findViewById(R.id.itemDescription3);
        price3= (EditText) findViewById(R.id.price3);

        spinnerG3= (Spinner) findViewById(R.id.spinnerG3);
        spinnerT3= (Spinner) findViewById(R.id.spinnerT3);
        spinnerS13= (Spinner) findViewById(R.id.spinnerS13);//s1==size
        spinnerS23= (Spinner) findViewById(R.id.spinnerS23);//s2== status
        spinnerC3= (Spinner) findViewById(R.id.spinnerC3);

        spinnerG3.setOnItemSelectedListener(this);
        spinnerT3.setOnItemSelectedListener(this);
        spinnerS23.setOnItemSelectedListener(this);
        spinnerC3.setOnItemSelectedListener(this);
        spinnerS13.setOnItemSelectedListener(this);

        ArrayAdapter<String> adp1= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, g);
        spinnerG3.setAdapter(adp1);
        g3= singleItem.getGender();
        spinnerG3.setSelection(g3);

        ArrayAdapter<String> adp2= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,t);
        spinnerT3.setAdapter(adp2);
        t3= singleItem.getType();
        spinnerT3.setSelection(t3);

        ArrayAdapter<String> adp3= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,s2);
        spinnerS23.setAdapter(adp3);
        s23= singleItem.getStatus();
        spinnerS23.setSelection(s23);

        ArrayAdapter<String> adp4= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,c);
        spinnerC3.setAdapter(adp4);
        c3= singleItem.getColor();
        spinnerC3.setSelection(c3);

        s13= singleItem.getSize();
        if (s13==555){
            spinnerS13.setVisibility(View.INVISIBLE);
        }
        else{
            if (t3==9){
                spinnerS13.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adp5= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sS1);
                spinnerS13.setAdapter(adp5);
                spinnerS13.setSelection(s13);
            }
            else{
                spinnerS13.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adp5= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sC1);
                spinnerS13.setAdapter(adp5);
                spinnerS13.setSelection(s13);
            }

        }

        price003= singleItem.getPrice();
        price03= String.valueOf(price003);
        price3.setText(price03);

        itemDescription03= singleItem.getItemDescription();
        itemDescription3.setText(itemDescription03);

        itemIdentify3= singleItem.getIdentify();

        itemPhoto03= singleItem.getItemPhoto();
        String help3= singleItem.getUserId()+ singleItem.getIdentify();
        storageItem= storage.getReference().child("item").child(help3);
        storageItem.getBytes(1024*1024*10).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                itemPhoto3.setImageBitmap(bitmap);
            }
        });

        oldItem= singleItem;
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
        if (adapterView==spinnerG3){
            g3= i;
        }
        if (adapterView==spinnerT3){
            t3= i;
            if ((i==0)||(i==7)||(i==8)){
                spinnerS13.setVisibility(View.INVISIBLE);
                s13=555;
                //שיניתי ל555 במקום 100
            }
            else if (i==9){
                spinnerS13.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adp5= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sS1);
                spinnerS13.setAdapter(adp5);
                s13=0;
            }
            else{
                spinnerS13.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adp5= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sC1);
                spinnerS13.setAdapter(adp5);
                s13=0;
            }
        }
        //לבדוק מה אפשר לעשות כדי לא לאפס לגמרי כמשתמש משתנה Type
        if (adapterView==spinnerS13){
            s13=i;
        }
        if (adapterView==spinnerS23){
            s23=i;
        }
        if (adapterView==spinnerC3){
            c3=i;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * AddPhoto3.
     * Short description- Checks where the user wants to upload the image from- Camera or Gallery.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void addPhoto3(View view) {
        changePhoto=true;
        photo3= new AlertDialog.Builder(this);
        photo3.setCancelable(false);
        photo3.setTitle("Update photo");
        photo3.setMessage("From where would you like to upload your photo?");
        photo3.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                camera3();
            }
        });
        photo3.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gallery3();
            }
        });
        AlertDialog photo03= photo3.create();
        photo03.show();
    }

    /**
     * Gallery3.
     * Short description- Moves from this activity to Gallery.
     */
    public void gallery3(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here"), PICK_IMAGE_REQUEST);
    }

    /**
     * Camera3.
     * Short description- Moves from this activity to Camera.
     */
    public void camera3(){
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
                itemPhoto3.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            itemPhoto3.setImageURI(filePath);
        }
    }

    /**
     * UpdateItem3.
     * Short description- Checks if the user really wants to change the information about the item.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void UpdateItem3(View view) {
        adb= new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle("Update Item");
        adb.setMessage("Would you like to update this item?");
        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                update();
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
     * Update.
     * Short description- Checks if the user has filled in all the required fields.
     * If the user has not filled in, a message is displayed to him.
     * Otherwise it updates the item in the database under the "Items" branch, and deletes and uploads again the item in the database under the "ItemsA" branch.
     */
    public void update(){
        if (changePhoto)
            itemPhoto03= filePath.getPath();
        else
            itemPhoto03=oldItem.getItemPhoto();

        if (g3 == 0) {
            Toast.makeText(this, "Enter gender", Toast.LENGTH_SHORT).show();
        } else if (t3 == 0) {
            Toast.makeText(this, "Enter the item's type", Toast.LENGTH_SHORT).show();
        } else if (s23 == 0) {
            Toast.makeText(this, "Enter the item's status", Toast.LENGTH_SHORT).show();
        } else if (c3 == 0) {
            Toast.makeText(this, "Enter the item's color", Toast.LENGTH_SHORT).show();
        }  else {
            price03 = price3.getText().toString();
            if (price03.isEmpty()) {
                Toast.makeText(this, "Enter the price", Toast.LENGTH_SHORT).show();
            } else {
                itemDescription03 = itemDescription3.getText().toString();
                price003 = Integer.parseInt(price03);
                uploadProfile3();
                newItem = new Item(uId, true, itemIdentify3, g3, t3, s23, c3, s13, price003, itemDescription03, itemPhoto03);

                refItemsA.child(String.valueOf(oldItem.getGender())).child(String.valueOf(oldItem.getType())).child(uId + itemIdentify3).removeValue();

                refItemsA.child(String.valueOf(g3)).child(String.valueOf(t3)).child(uId + itemIdentify3).setValue(newItem);
                refItems.child(uId + itemIdentify3).setValue(newItem);
                Intent next = new Intent(this, UserProfile.class);
                startActivity(next);
            }
        }
    }

    /**
     * UploadProfile3.
     * Short description- Uploads the user's item image to Firebase Storage.
     */
    public void uploadProfile3(){
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = storageReference.child("item/"+uId+itemIdentify3);
            ref.putFile(filePath).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateItem.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(UpdateItem.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
}