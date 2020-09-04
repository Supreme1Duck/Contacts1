package com.example.contacts;

import java.io.Serializable;

public class Contact implements Serializable {
    private String contactNumber;
    private String holderName;
    private int resourceId;

    Contact(int id, String PN, String HN) {
        contactNumber = PN;
        holderName = HN;
        resourceId = id;
    }

    public String getPhoneNumber() {
        return contactNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public int getResourceId() {
        return resourceId;
    }
}
