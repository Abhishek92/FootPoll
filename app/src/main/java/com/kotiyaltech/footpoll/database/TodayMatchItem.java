package com.kotiyaltech.footpoll.database;

/**
 * Created by hp pc on 11-06-2018.
 */

public class TodayMatchItem {
    private String matchNo;
    private String date;
    private String day;
    private String time;
    private String teamOne;
    private String teamTwo;
    private String teamOneFlag;
    private String teamTwoFlag;
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
