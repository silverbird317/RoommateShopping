package edu.uga.cs.roommateshopping;

import java.io.Serializable;

/*
 * class that holds info from shopping item
 */
public class ShoppingItem implements Serializable {

    private String key;
    private String item;

    private double price;
    private int amount;

    /*
     * empty constructor fo firebase
     */
    public ShoppingItem() {}

    /*
     * public constructor to instantiate item and score and details
     */
    public ShoppingItem(String item, int amount, double price) {
        this.item = item;
        this.amount = amount;
        this.price = price;
    }

    /*
     * key getter
     */
    public String getKey() {
        return key;
    }

    /*
     * key setter
     */
    public void setKey(String key) {
        this.key = key;
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
    public void setItem(String item)
    {
        this.item = item;
    }

    /*
     * amount getter
     */
    public int getAmount()
    {
        return amount;
    }

    /*
     * amount setter
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    /*
     * price getter
     */
    public double getPrice() {
        return price;
    }

    /*
     * price setter
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /*
     * toString override
     */
    public String toString() {
        return "Item: " + item + "\nAmount: " + amount + "\nDetails: " + price;
    }
}
