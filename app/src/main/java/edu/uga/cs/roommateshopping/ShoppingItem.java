package edu.uga.cs.roommateshopping;

import java.io.Serializable;

public class ShoppingItem implements Serializable {

    private String key;
    private String item;
    private int amount;
    private String details;

    public ShoppingItem() {}

    /*
     * public constructor to instantiate item and score and details
     */
    public ShoppingItem(String item, int amount, String details) {
        this.item = item;
        this.amount = amount;
        this.details = details;
    }

    public String getKey() {
        return key;
    }

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

    public String getDetails() {
        return details;
    }
    /*
     * amount setter
     */
    public void setDetails(String details)
    {
        this.details = details;
    }

    /*
     * toString override
     */
    public String toString() {
        return "Item: " + item + "\nAmount: " + amount + "\nDetails: " + details;
    }
}
