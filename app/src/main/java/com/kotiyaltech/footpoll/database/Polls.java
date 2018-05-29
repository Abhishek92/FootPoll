package com.kotiyaltech.footpoll.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp pc on 01-04-2018.
 */

public class Polls {
    @SerializedName("polls")
    @Expose
    private List<Poll> polls = null;

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }
}
