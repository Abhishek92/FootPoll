package com.kotiyaltech.footpoll.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 11-06-2018.
 */

public class TodayMatch {
    private List<TodayMatchItem> todayMatch = new ArrayList<>();

    public List<TodayMatchItem> getTodayMatch() {
        return todayMatch;
    }

    public void setTodayMatch(List<TodayMatchItem> todayMatch) {
        this.todayMatch = todayMatch;
    }



}
