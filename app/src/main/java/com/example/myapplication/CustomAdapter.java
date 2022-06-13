package com.example.myapplication;

import static com.example.myapplication.FBref.storage;
import static com.example.myapplication.FBref.storageItem;
import static com.example.myapplication.FBref.storageUser;
import static com.example.myapplication.MainActivity.uId;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

/**
 * Custom Adapter.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- This class creates a custom list view.
 */

public class CustomAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Item> tempItems;
    LayoutInflater inflter;

    public CustomAdapter(Activity applicationContext, ArrayList<Item> tempItems) {
        this.context = applicationContext;
        this.tempItems=tempItems;
        inflter= context.getLayoutInflater();
        //inflter = (LayoutInflater.from(applicationContext));
    }



    @Override
    public int getCount() {
        return tempItems.size();
    }

    @Override
    public Object getItem(int i) {
        return tempItems;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_lv, null);
        TextView itemText = (TextView) view.findViewById(R.id.itemText);
        ImageView photo = (ImageView) view.findViewById(R.id.photo);
        itemText.setText(tempItems.get(i).getItemDescription()+"\n"+"\n"+"\n"+
                tempItems.get(i).getPrice());
        String help= tempItems.get(i).getUserId()+tempItems.get(i).getIdentify();
        storageItem= storage.getReference().child("item").child(help);
        storageItem.getBytes(1024*1024*10).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                photo.setImageBitmap(bitmap);
            }
        });

        return view;
    }
}
