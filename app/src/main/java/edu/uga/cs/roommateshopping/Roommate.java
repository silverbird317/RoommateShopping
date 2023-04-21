package edu.uga.cs.roommateshopping;

import java.io.Serializable;

public class Roommate implements Serializable {

    private long cid;
    private String name;
    private String email;
    private String phone;

    /*
     * public constructor to instantiate name and score and phone
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

    public String getPhone() {
        return phone;
    }
    /*
     * email setter
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
