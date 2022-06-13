package com.example.myapplication;

/**
 * Item.
 * @author		Liad Peretz <lp8186@bs.amalnet.k12.il>
 * @version     2.0
 * @since		14/02/2022
 * Short Description- This class creates a new object called "Item".
 */

public class Item {
    private String userId;
    private boolean active;
    private int identify;
    private int gender;
    private int type;
    private int status;
    private int color;
    private int size;
    private int price;
    private String itemDescription;
    private String itemPhoto;

    /**
     * Item.
     * Short description- Creates a new object of type "Item" according to given parameters.
     * <p>
     *      String userId
     *      boolean active
     *      int identify
     *      int gender
     *      int type
     *      int status
     *      int color
     *      int size
     *      int price
     *      String itemDescription
     *      String itemPhoto
     * @param userId- the user Id from Firebase Authentication.
     * @param active- if the item is still for selling.
     * @param identify- the item's num.
     * @param gender- the item's gender.
     * @param type- the item's type.
     * @param status- the item's status.
     * @param color- the item's color.
     * @param size- the item's size(if it has).
     * @param price- the item's price.
     * @param itemDescription- the item's description.
     * @param itemPhoto- Link to the item image.
     */
    public Item (String userId,boolean active, int identify, int gender, int type, int status, int color, int size, int price, String itemDescription, String itemPhoto){
        this.userId= userId;
        this.active= active;
        this.identify= identify;
        this.gender= gender;
        this.type= type;
        this.status= status;
        this.color= color;
        this.size= size;
        this.price= price;
        this.itemDescription= itemDescription;
        this.itemPhoto= itemPhoto;
    }
    /**
     * Item.
     * Short description- Creates a new and empty object of type "Item"
     */
    public Item(){
        this.userId= null;
        this.active= Boolean.parseBoolean(null);
        this.identify= 0;
        this.gender= 0;
        this.type= 0;
        this.status= 0;
        this.color= 0;
        this.size= 0;
        this.price= 0;
        this.itemDescription= null;
        this.itemPhoto= null;
    }
    public String getUserId(){
        return userId;
    }
    public boolean getActive(){
        return active;
    }
    public int getIdentify(){
        return identify;
    }
    public int getGender(){
        return gender;
    }
    public int getType(){
        return type;
    }
    public int getStatus(){
        return status;
    }
    public int getColor(){
        return color;
    }
    public int getSize(){
        return size;
    }
    public int getPrice(){
        return price;
    }
    public String getItemDescription(){
        return itemDescription;
    }
    public String getItemPhoto(){
        return itemPhoto;
    }
    public void setUserId(String userId){this.userId=userId;}
    public void setActive(boolean active){
        this.active=active;
    }
    public void setIdentify(int identify){this.identify=identify;}
    public void setGender(int gender){this.gender=gender;}
    public void setType(int type){this.type=type;}
    public void setStatus(int status){this.status=status;}
    public void setColor(int color){this.color=color;}
    public void setSize(int size){this.size=size;}
    public void setPrice(int price){this.price=price;}
    public void setItemDescription(String itemDescription){this.itemDescription=itemDescription;}
    public void setItemPhoto(String itemPhoto){this.itemPhoto=itemPhoto;}
}
