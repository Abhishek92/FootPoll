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
public class ResultsFragment extends Fragment {

    public static final String TAG = ResultsFragment.class.getSimpleName();

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance(){
        return new ResultsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

}
