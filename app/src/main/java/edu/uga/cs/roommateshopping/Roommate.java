package edu.uga.cs.roommateshopping;

import java.io.Serializable;

/*
 * class to hold roommate object
 */
public class Roommate implements Serializable {

    private String key;
    private String name;
    private String email;
    private String phone;

    /*
     * empty constructor for firebase
     */
    public Roommate() {}

    /*
     * public constructor to instantiate name and email and phone
     */
    public Roommate(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /*
     * name getter
     */
    public String getName()
    {
        return name;
    }

    /*
     * name setter
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /*
     * email getter
     */
    public String getEmail()
    {
        return email;
    }

    /*
     * email setter
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /*
     * phone getter
     */
    public String getPhone() {
        return phone;
    }
    /*
     * phone setter
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /*
     * toString override
     */
    public String toString() {
        return "Name: " + name + "\nEmail: " + email + "\nPhone: " + phone;
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
}
