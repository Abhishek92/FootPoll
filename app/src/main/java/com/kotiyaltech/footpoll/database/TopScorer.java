package com.kotiyaltech.footpoll.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 15-06-2018.
 */

public class TopScorer {
    @SerializedName("topScorer")
    @Expose
    private List<TopScorerItem> topScorer = new ArrayList<>();

    public List<TopScorerItem> getTopScorer() {
        return topScorer;
    }

    public void setTopScorer(List<TopScorerItem> topScorer) {
        this.topScorer = topScorer;
    }
}
