package com.kotiyaltech.footpoll.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp pc on 26-05-2018.
 */

public class PointsTable {

    @SerializedName("pointsTable")
    @Expose
    private List<PointItem> pointsTable = null;

    public List<PointItem> getPointsTable() {
        return pointsTable;
    }

    public void setPointsTable(List<PointItem> pointsTable) {
        this.pointsTable = pointsTable;
    }
}
