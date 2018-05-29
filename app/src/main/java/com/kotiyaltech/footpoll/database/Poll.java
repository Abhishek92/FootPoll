package com.kotiyaltech.footpoll.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 01-04-2018.
 */

public class Poll implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("teamA")
    @Expose
    private String teamA;
    @SerializedName("teamB")
    @Expose
    private String teamB;
    @SerializedName("teamAVotes")
    @Expose
    private Integer teamAVotes;
    @SerializedName("teamBVotes")
    @Expose
    private Integer teamBVotes;
    @SerializedName("votedUsers")
    @Expose
    private List<VotedUser> votedUsers = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public Integer getTeamAVotes() {
        return teamAVotes;
    }

    public void setTeamAVotes(Integer teamAVotes) {
        this.teamAVotes = teamAVotes;
    }

    public Integer getTeamBVotes() {
        return teamBVotes;
    }

    public void setTeamBVotes(Integer teamBVotes) {
        this.teamBVotes = teamBVotes;
    }

    public void voteForTeamA(){
        teamAVotes = teamAVotes + 1;
    }

    public void addVotedUsers(VotedUser votedUser){
        votedUsers.add(votedUser);
    }

    public void voteForTeamB(){
        teamBVotes = teamBVotes + 1;
    }

    public List<VotedUser> getVotedUsers() {
        return votedUsers;
    }

    public void setVotedUsers(List<VotedUser> votedUsers) {
        this.votedUsers = votedUsers;
    }

    public Poll(){

    }

    protected Poll(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        question = in.readString();
        teamA = in.readString();
        teamB = in.readString();
        teamAVotes = in.readByte() == 0x00 ? null : in.readInt();
        teamBVotes = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            votedUsers = new ArrayList<VotedUser>();
            in.readList(votedUsers, VotedUser.class.getClassLoader());
        } else {
            votedUsers = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(question);
        dest.writeString(teamA);
        dest.writeString(teamB);
        if (teamAVotes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(teamAVotes);
        }
        if (teamBVotes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(teamBVotes);
        }
        if (votedUsers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(votedUsers);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Poll> CREATOR = new Creator<Poll>() {
        @Override
        public Poll createFromParcel(Parcel in) {
            return new Poll(in);
        }

        @Override
        public Poll[] newArray(int size) {
            return new Poll[size];
        }
    };
}