package com.pluralsight.dagger2example.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Date;

@ParcelablePlease
public class Repository implements Parcelable {
    @Expose
    String id;
    @Expose
    String name;
    @Expose
    String description;
    @Expose
    Owner owner;
    @Expose
    Date created_at;
    @Expose
    Integer stargazers_count;
    @Expose
    String language;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(Integer stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static final Creator<Repository> CREATOR = new Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel in) {
            Repository repository = new Repository();
            RepositoryParcelablePlease.readFromParcel(repository, in);
            return repository;
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        RepositoryParcelablePlease.writeToParcel(this, parcel, flags);
    }
}
