package com.kotiyaltech.footpoll.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hp pc on 12-06-2018.
 */

public class ResultItem {

    @SerializedName("matchNo")
    @Expose
    private String matchNo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("teamOne")
    @Expose
    private String teamOne;
    @SerializedName("teamTwo")
    @Expose
    private String teamTwo;
    @SerializedName("teamOneGoal")
    @Expose
    private Integer teamOneGoal;
    @SerializedName("teamTwoGoal")
    @Expose
    private Integer teamTwoGoal;
    @SerializedName("teamOneFlag")
    @Expose
    private String teamOneFlag;
    @SerializedName("teamTwoFlag")
    @Expose
    private String teamTwoFlag;
    @SerializedName("stage")
    @Expose
    private String stage;

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(String teamOne) {
        this.teamOne = teamOne;
    }

    public String getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(String teamTwo) {
        this.teamTwo = teamTwo;
    }

    public Integer getTeamOneGoal() {
        return teamOneGoal;
    }

    public void setTeamOneGoal(Integer teamOneGoal) {
        this.teamOneGoal = teamOneGoal;
    }

    public Integer getTeamTwoGoal() {
        return teamTwoGoal;
    }

    public void setTeamTwoGoal(Integer teamTwoGoal) {
        this.teamTwoGoal = teamTwoGoal;
    }

    public String getTeamOneFlag() {
        return teamOneFlag;
    }

    public void setTeamOneFlag(String teamOneFlag) {
        this.teamOneFlag = teamOneFlag;
    }

    public String getTeamTwoFlag() {
        return teamTwoFlag;
    }

    public void setTeamTwoFlag(String teamTwoFlag) {
        this.teamTwoFlag = teamTwoFlag;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
