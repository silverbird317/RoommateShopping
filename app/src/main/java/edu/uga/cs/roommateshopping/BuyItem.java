package edu.uga.cs.roommateshopping;

import java.io.Serializable;

public class BuyItem implements Serializable {

    private long cid;
    private String item;
    private double price;
    private int quantity;

    /*
     * public constructor to instantiate item and score and details
     */
    public BuyItem(String item, double price, int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    /*
     * item getter
     */
    public String getItem()
    {
        return item;
    }

    /*
     * item setter
     */
    public void setItem(String Item)
    {
        this.item = item;
    }

    /*
     * amount getter
     */
    public double getPrice()
    {
        return price;
    }

    /*
     * amount setter
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    /*
     * amount setter
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    /*
     * toString override
     */
    public String toString() {
        return "Item: " + item + "\nAmount: " + price + "\nDetails: " + quantity;
    }

    /*
     * cid getter
     */
    public long getCid() {
        return cid;
    }

    /*
     * cid setter
     */
    public void setCid(long cid) {
        this.cid = cid;
    }
}
