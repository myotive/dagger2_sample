package com.pluralsight.dagger2example.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.io.Serializable;

@ParcelablePlease
public class Owner implements Parcelable{
    @Expose
    String id;
    @Expose
    String login;
    @Expose
    String avatar_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            Owner target = new Owner();
            OwnerParcelablePlease.readFromParcel(target, in);
            return target;
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        OwnerParcelablePlease.writeToParcel(this, parcel, i);
    }
}
