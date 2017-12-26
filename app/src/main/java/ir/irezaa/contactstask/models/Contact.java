package ir.irezaa.contactstask.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rezapilehvar on 26/12/2017 AD.
 */

public class Contact implements Serializable {

    @Expose
    @SerializedName("phone")
    private String phoneNumber;

    @Expose
    @SerializedName("name")
    private String fullName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return hashCode() == contact.hashCode();
    }

    @Override
    public int hashCode() {
        return (phoneNumber + fullName).hashCode();
    }
}
