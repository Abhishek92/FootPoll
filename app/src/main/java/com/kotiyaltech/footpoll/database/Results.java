package com.kotiyaltech.footpoll.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 12-06-2018.
 */

public class Results {
    @SerializedName("results")
    @Expose
    private List<ResultItem> results = new ArrayList<>();

    public List<ResultItem> getResults() {
        return results;
    }

    public void setResults(List<ResultItem> results) {
        this.results = results;
    }
}
