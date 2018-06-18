package com.kotiyaltech.footpoll.database;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hp pc on 26-05-2018.
 */

public class PointItem implements Comparable<PointItem> {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("teamName")
    @Expose
    private String teamName;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("win")
    @Expose
    private Integer win;
    @SerializedName("loss")
    @Expose
    private Integer loss;
    @SerializedName("matchesPlayed")
    @Expose
    private Integer matchesPlayed;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("stage")
    @Expose
    private String stage;
    @SerializedName("draw")
    @Expose
    private Integer draw;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLoss() {
        return loss;
    }

    public void setLoss(Integer loss) {
        this.loss = loss;
    }

    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(Integer matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }


    @Override
    public int compareTo(@NonNull PointItem pointItem) {
        if (this.points > pointItem.getPoints())
            return -1;
        if (this.points < pointItem.getPoints())
            return 1;
        else return 0;
    }
}
