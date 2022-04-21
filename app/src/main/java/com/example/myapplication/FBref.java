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
    public static StorageReference storageUser;
    public static StorageReference storageItem;

    public static FirebaseDatabase FBDB= FirebaseDatabase.getInstance();
    public static DatabaseReference refUsers= FBDB.getReference("Users");
    public static DatabaseReference refItemsA= FBDB.getReference("ItemsA");
    public static DatabaseReference refItemsD= FBDB.getReference("ItemsD");
    public static DatabaseReference refItems= FBDB.getReference("Items");

    public static DatabaseReference refItemsA2,refItemsA3;
}
