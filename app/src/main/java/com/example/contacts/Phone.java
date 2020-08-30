package com.example.contacts;

import android.os.Parcelable;

import java.io.Serializable;

public class Phone implements Serializable {
    private String PhoneNumber;
    private String HolderName;
    private int ResourceId;

    Phone(int id, String PN, String HN){
        PhoneNumber = PN;
        HolderName = HN;
        ResourceId = id;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getHolderName() {
        return HolderName;
    }

    public int getResourceId() {
        return ResourceId;
    }
}
