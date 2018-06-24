package com.kotiyaltech.footpoll.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.adapter.ResultListAdapter;
import com.kotiyaltech.footpoll.database.ResultItem;
import com.kotiyaltech.footpoll.database.Results;
import com.kotiyaltech.footpoll.viewmodel.ResultsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {

    public static final String TAG = ResultsFragment.class.getSimpleName();
    private RecyclerView resultsRecyclerView;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultsRecyclerView = view.findViewById(R.id.result_list_rv);
        loadAd(view);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        resultsRecyclerView.setLayoutManager(linearLayoutManager);

        ResultsViewModel resultsViewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        resultsViewModel.getResults().observe(this, new Observer<Results>() {
            @Override
            public void onChanged(@Nullable Results results) {
                progressBar.setVisibility(View.GONE);
                if(null != results)
                    loadResults(results.getResults());
            }
        });
    }

    private void loadAd(@NonNull View view) {
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void loadResults(List<ResultItem> results) {
        ResultListAdapter votedUserListAdapter = new ResultListAdapter(results, getContext());
        resultsRecyclerView.setAdapter(votedUserListAdapter);
    }


}
