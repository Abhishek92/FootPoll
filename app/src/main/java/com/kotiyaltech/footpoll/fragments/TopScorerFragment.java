package com.kotiyaltech.footpoll.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotiyaltech.footpoll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopScorerFragment extends Fragment {

    public static final String TAG = TopScorerFragment.class.getSimpleName();
    public TopScorerFragment() {
        // Required empty public constructor
    }

    public static TopScorerFragment newInstance(){
        return new TopScorerFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_scorer, container, false);
    }

}
