package com.kotiyaltech.footpoll.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp pc on 28-05-2018.
 */

public class ScheduleItem implements Parcelable {

    private String teamOne;

    private String time;

    private String teamTwo;

    private String day;

    private String date;

    private String matchNo;

    private String stage;

    public String getTeamOne ()
    {
        return teamOne;
    }

    public void setTeamOne (String teamOne)
    {
        this.teamOne = teamOne;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getTeamTwo ()
    {
        return teamTwo;
    }

    public void setTeamTwo (String teamTwo)
    {
        this.teamTwo = teamTwo;
    }

    public String getDay ()
    {
        return day;
    }

    public void setDay (String day)
    {
        this.day = day;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getMatchNo ()
    {
        return matchNo;
    }

    public void setMatchNo (String matchNo)
    {
        this.matchNo = matchNo;
    }

    public String getStage ()
    {
        return stage;
    }

    public void setStage (String stage)
    {
        this.stage = stage;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [teamOne = "+teamOne+", time = "+time+", teamTwo = "+teamTwo+", day = "+day+", date = "+date+", matchNo = "+matchNo+", stage = "+stage+"]";
    }

    public ScheduleItem(){

    }

    protected ScheduleItem(Parcel in) {
        teamOne = in.readString();
        time = in.readString();
        teamTwo = in.readString();
        day = in.readString();
        date = in.readString();
        matchNo = in.readString();
        stage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teamOne);
        dest.writeString(time);
        dest.writeString(teamTwo);
        dest.writeString(day);
        dest.writeString(date);
        dest.writeString(matchNo);
        dest.writeString(stage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ScheduleItem> CREATOR = new Parcelable.Creator<ScheduleItem>() {
        @Override
        public ScheduleItem createFromParcel(Parcel in) {
            return new ScheduleItem(in);
        }

        @Override
        public ScheduleItem[] newArray(int size) {
            return new ScheduleItem[size];
        }
    };
}