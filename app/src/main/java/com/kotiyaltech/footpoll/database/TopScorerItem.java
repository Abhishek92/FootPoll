package com.kotiyaltech.footpoll.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hp pc on 15-06-2018.
 */

public class TopScorerItem {
    @SerializedName("playerName")
    @Expose
    private String playerName;
    @SerializedName("teamName")
    @Expose
    private String teamName;
    @SerializedName("teamFlag")
    @Expose
    private String teamFlag;
    @SerializedName("goals")
    @Expose
    private String goals;
    @SerializedName("assist")
    @Expose
    private String assist;
    @SerializedName("minutesPlayed")
    @Expose
    private String minutesPlayed;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamFlag() {
        return teamFlag;
    }

    public void setTeamFlag(String teamFlag) {
        this.teamFlag = teamFlag;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getAssist() {
        return assist;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }

    public String getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(String minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }
}
