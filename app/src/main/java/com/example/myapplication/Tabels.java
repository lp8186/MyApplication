package com.example.myapplication;

import static com.example.myapplication.FBref.FBDB;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Tabels.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- This class contains references to Firebase.
 */

public class Tabels {
    public static DatabaseReference refTabels= FBDB.getReference("Tabels");
    public static DatabaseReference refGender= refTabels.child("Gender");
    public static DatabaseReference refType= refTabels.child("Type");
    public static DatabaseReference refStatus= refTabels.child("Status");
    public static DatabaseReference refColor= refTabels.child("Color");
    public static DatabaseReference refSize= refTabels.child("Size");

    public static DatabaseReference refSizeC= refSize.child("Clothes");
    public static DatabaseReference refSizeS= refSize.child("Shoes");


    public static ArrayList<String> gender = new ArrayList<String>();
    public static ArrayList<String> type = new ArrayList<String>();
    public static ArrayList<String> status = new ArrayList<String>();
    public static ArrayList<String> color = new ArrayList<String>();
    public static ArrayList<String> sizeC = new ArrayList<String>();
    public static ArrayList<String> sizeS = new ArrayList<String>();

    public static ArrayList<String> gender1(){
        refGender.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                gender.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    String temp= (String) data.getValue();
                    gender.add(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return gender;
    }
    public static ArrayList<String> type1(){
        refType.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                type.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    String temp= (String) data.getValue();
                    type.add(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return type;
    }
    public static ArrayList<String> status1(){
        refStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                status.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    String temp= (String) data.getValue();
                    status.add(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return status;
    }
    public static ArrayList<String> color1(){
        refColor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                color.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    String temp= (String) data.getValue();
                    color.add(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return color;
    }
    public static ArrayList<String> sizeC1(){
        refSizeC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                sizeC.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    String temp= (String) data.getValue();
                    sizeC.add(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return sizeC;
    }
    public static ArrayList<String> sizeS1(){
        refSizeS.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                sizeS.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    String temp= String.valueOf( data.getValue());
                    sizeS.add(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return sizeS;
    }

}
