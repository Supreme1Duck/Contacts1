package com.example.contacts;

import java.io.Serializable;

public class Phone implements Serializable {
    private String phoneNumber;
    private String holderName;
    private int resourceId;

    Phone(int id, String PN, String HN) {
        phoneNumber = PN;
        holderName = HN;
        resourceId = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public int getResourceId() {
        return resourceId;
    }
}
