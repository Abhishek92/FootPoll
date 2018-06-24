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
import com.kotiyaltech.footpoll.adapter.TopScorerAdapter;
import com.kotiyaltech.footpoll.database.TopScorer;
import com.kotiyaltech.footpoll.database.TopScorerItem;
import com.kotiyaltech.footpoll.viewmodel.TopScorerViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopScorerFragment extends Fragment {

    public static final String TAG = TopScorerFragment.class.getSimpleName();
    private RecyclerView topScorerRecyclerView;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topScorerRecyclerView = view.findViewById(R.id.topScorrer_list_rv);
        loadAd(view);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topScorerRecyclerView.setLayoutManager(linearLayoutManager);

        TopScorerViewModel topScorerViewModel = ViewModelProviders.of(this).get(TopScorerViewModel.class);
        topScorerViewModel.getTopScorer().observe(this, new Observer<TopScorer>() {
            @Override
            public void onChanged(@Nullable TopScorer topScorer) {
                progressBar.setVisibility(View.GONE);
                if (null != topScorer)
                    loadTopScorer(topScorer.getTopScorer());
            }
        });
    }

    private void loadAd(@NonNull View view) {
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void loadTopScorer(List<TopScorerItem> results) {
        TopScorerAdapter topScorerAdapter = new TopScorerAdapter(results, getContext());
        topScorerRecyclerView.setAdapter(topScorerAdapter);
    }

}
