package com.example.myapplication;

public class User {
    private String userId;
    private String name;
    private String phone;
    private String city;
    private String description;
    private String photo;
    private int itemsNum;

    public User(String userId, String name, String phone, String city, String description, String photo, int itemsNum){
        this.userId= userId;
        this.name= name;
        this.phone= phone;
        this.city= city;
        this.description= description;
        this.photo= photo;
        this.itemsNum= itemsNum;
    }
    public User(){
        this.userId= null;
        this.name= null;
        this.phone= null;
        this.city= null;
        this.description= null;
        this.photo= null;
        this.itemsNum= 0;
    }
    public String getUserId(){return userId;}
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
    public String getCity(){
        return city;
    }
    public String getDescription(){
        return description;
    }
    public String getPhoto(){
        return photo;
    }
    public int getItemsNum() {return itemsNum;}

    public void setUserId(String userId){this.userId=userId;}
    public void setName(String name){
        this.name=name;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public void setCity(String city){
        this.city=city;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setPhoto(String photo){
        this.photo=photo;
    }
    public void setItemsNum(int itemsNum) {this.itemsNum=itemsNum;}
}
