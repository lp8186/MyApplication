package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    public static FirebaseAuth mAuth= FirebaseAuth.getInstance();
    public static FirebaseUser currentUser;

    public static FirebaseStorage storage= FirebaseStorage.getInstance();
    public static StorageReference storageReference = storage.getReference();

    public static FirebaseDatabase FBDB= FirebaseDatabase.getInstance();
    public static DatabaseReference refUsers= FBDB.getReference("Users");
}
