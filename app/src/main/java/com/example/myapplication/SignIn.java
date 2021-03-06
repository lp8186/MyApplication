package com.example.myapplication;

import static com.example.myapplication.FBref.currentUser;
import static com.example.myapplication.FBref.mAuth;
import static com.example.myapplication.FBref.refUsers;
import static com.example.myapplication.FBref.storageReference;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.example.myapplication.MainActivity.uId;

/**
 * Sign In.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- In this activity a new user sign in to the app.
 */

public class SignIn extends AppCompatActivity {
    EditText name,phone,email2,password2,city,description;
    String userId02,name02,phone02,email02,password02,city02,description02,profile02;

    ImageView profile;
    AlertDialog.Builder photo1;
    boolean checkProfile=false;
    User newUser;

    private final int PICK_IMAGE_REQUEST = 22, CAMERA_REQUEST=24;
    Uri filePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        name= (EditText) findViewById(R.id.name);
        phone= (EditText) findViewById(R.id.phone);
        email2= (EditText) findViewById(R.id.email2);
        password2= (EditText) findViewById(R.id.password2);
        city= (EditText) findViewById(R.id.city);
        description= (EditText) findViewById(R.id.description);
        profile= (ImageView) findViewById(R.id.profile);


    }

    /**
     * AddPhoto.
     * Short description- Checks where the user wants to upload the image from- Camera or Gallery.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void addPhoto(View view) {
        photo1= new AlertDialog.Builder(this);
        photo1.setCancelable(false);
        photo1.setTitle("Add photo");
        photo1.setMessage("From where would you like to upload your photo?");
        photo1.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                camera();
            }
        });
        photo1.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gallery();
            }
        });
        AlertDialog photo01= photo1.create();
        photo01.show();

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
                profile.setImageBitmap(bitmap);
                checkProfile=true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            profile.setImageURI(filePath);
            checkProfile=true;
        }
    }

    /**
     * SingIn.
     * Short description- Checks if the user has filled in all the required fields.
     * If the user has not filled in, a message is displayed to him.
     * Otherwise it registers the user in the Firebase Authentication service and uploads the new user to the database under the "Users" branch.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void singIn(View view) {
        if (!(checkProfile)){
            Toast.makeText(this, "Enter a photo", Toast.LENGTH_SHORT).show();
        }
        else{
            profile02= filePath.getPath();
            name02 = name.getText().toString();
            if (name02.isEmpty()) {
                Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            } else {
                phone02 = phone.getText().toString();
                if (phone02.isEmpty()) {
                    Toast.makeText(this, "Enter your phone", Toast.LENGTH_SHORT).show();
                }
                else if(checkPhoneNum()){
                    Toast.makeText(this, "Wrong phone num", Toast.LENGTH_SHORT).show();
                }
                else {
                    email02 = email2.getText().toString();
                    if (email02.isEmpty()) {
                        Toast.makeText(this, "Enter your mail", Toast.LENGTH_SHORT).show();
                    } else {
                        password02 = password2.getText().toString();
                        if (password02.isEmpty()) {
                            Toast.makeText(this, "Enter a password", Toast.LENGTH_SHORT).show();
                        } else {
                            city02 = city.getText().toString();
                            if (city02.isEmpty()) {
                                Toast.makeText(this, "Enter your city", Toast.LENGTH_SHORT).show();
                            } else {
                                    description02 = description.getText().toString();
                                    mAuth.createUserWithEmailAndPassword(email02, password02)
                                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Log.d("TAG", "createUserWithEmail:success");
                                                        currentUser = mAuth.getCurrentUser();
                                                        userId02 = currentUser.getUid();
                                                        uId = currentUser.getUid();
                                                        uploadProfile();
                                                        newUser = new User(userId02, name02, phone02, city02, description02, profile02,0);
                                                        refUsers.child(userId02).setValue(newUser);
                                                        createSharedPreferences();
                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                                        Toast.makeText(SignIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * CheckPhoneNum.
     * Short descriptions- Checks whether the phone num entered by the user is real (according to the conditions of a telephone number in Israel).
     * @return true if the phone num is real, false if not.
     */
    private boolean checkPhoneNum(){
        if (phone02.startsWith("05")){
            if (phone02.length()==10)
                return false;
        }
        return true;
    }

    /**
     * UploadProfile.
     * Short description- Uploads the user's profile image to Firebase Storage.
     */
    private void uploadProfile() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = storageReference.child("profile/"+userId02);
            ref.putFile(filePath).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(SignIn.this, "Hello new user!", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(SignIn.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
     * CreateSharedPreferences.
     * Short description- Creates Shared Preferences.
     */
    public void createSharedPreferences(){
        SharedPreferences settings=getSharedPreferences("SortingInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        //editor.putInt("counter",0);
        editor.putInt("gender",0);
        editor.putInt("type",0);
        editor.putInt("status",0);
        editor.putInt("color",0);
        editor.putInt("size",555);
        editor.commit();

        Intent moveToAllItems2= new Intent(this,AllItems.class);
        startActivity(moveToAllItems2);
    }
}