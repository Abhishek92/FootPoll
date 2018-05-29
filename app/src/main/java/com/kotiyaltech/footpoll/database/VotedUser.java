package com.kotiyaltech.footpoll.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hp pc on 07-04-2018.
 */

public class VotedUser implements Parcelable {
    @SerializedName("teamVoted")
    @Expose
    private String teamVoted;
    @SerializedName("email")
    @Expose
    private String email;
    private String name;
    private String imageUrl;
    private String uId;

    public String getTeamVoted() {
        return teamVoted;
    }

    public void setTeamVoted(String teamVoted) {
        this.teamVoted = teamVoted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotedUser votedUser = (VotedUser) o;

        return email != null ? email.equals(votedUser.email) : votedUser.email == null
                || uId != null ? uId.equals(votedUser.uId) : votedUser.uId == null;
    }

    @Override
    public int hashCode() {
        return uId != null ? uId.hashCode() : 0;
    }

    public VotedUser(){

    }

    protected VotedUser(Parcel in) {
        teamVoted = in.readString();
        email = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        uId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teamVoted);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(uId);
    }

    @SuppressWarnings("unused")
    public static final Creator<VotedUser> CREATOR = new Creator<VotedUser>() {
        @Override
        public VotedUser createFromParcel(Parcel in) {
            return new VotedUser(in);
        }

        @Override
        public VotedUser[] newArray(int size) {
            return new VotedUser[size];
        }
    };
}