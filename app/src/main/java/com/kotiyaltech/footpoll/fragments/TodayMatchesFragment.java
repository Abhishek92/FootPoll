package com.kotiyaltech.footpoll.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.database.TodayMatch;
import com.kotiyaltech.footpoll.database.TodayMatchItem;
import com.kotiyaltech.footpoll.viewmodel.ScheduleViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayMatchesFragment extends Fragment {


    public static final String TAG = TodayMatchesFragment.class.getSimpleName();
    private ScheduleViewModel scheduleViewModel;
    private LinearLayout pointsTeamItem;
    private TextView dateTv;

    public TodayMatchesFragment() {
        // Required empty public constructor
    }

    public static TodayMatchesFragment newInstance(){
        return new TodayMatchesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_matches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pointsTeamItem = view.findViewById(R.id.schedule_team_item_container);
        dateTv = view.findViewById(R.id.dateText);

        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        scheduleViewModel.getTodayMatch().observe(this, new Observer<TodayMatch>() {
            @Override
            public void onChanged(@Nullable TodayMatch schedule) {
                //progressBar.setVisibility(View.GONE);
                if(null != schedule)
                    loadSchedule(schedule.getTodayMatch());
            }
        });
    }

    private void loadSchedule(List<TodayMatchItem> todayMatchList) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < todayMatchList.size(); i++) {
            TodayMatchItem pointItem = todayMatchList.get(i);
            dateTv.setText(pointItem.getDate());
            RelativeLayout pointsTeamItemLayout = (RelativeLayout) layoutInflater.inflate(R.layout.today_match_team_item_layout, null);
            TextView teamOne = pointsTeamItemLayout.findViewById(R.id.teamOneTxt);
            TextView teamTwo = pointsTeamItemLayout.findViewById(R.id.teamTwoTxt);
            TextView day = pointsTeamItemLayout.findViewById(R.id.dayTxt);
            TextView timeTxt = pointsTeamItemLayout.findViewById(R.id.timeTxt);
            ImageView teamOneFlag = pointsTeamItemLayout.findViewById(R.id.teamOneImg);
            ImageView teamTwoFlag = pointsTeamItemLayout.findViewById(R.id.teamTwoImg);
            View seperatorLine = pointsTeamItemLayout.findViewById(R.id.seperator_line);

            Glide.with(this).load(pointItem.getTeamOneFlag()).into(teamOneFlag);
            Glide.with(this).load(pointItem.getTeamTwoFlag()).into(teamTwoFlag);
            teamOne.setText(pointItem.getTeamOne());
            teamTwo.setText(pointItem.getTeamTwo());
            day.setText(pointItem.getDay());
            timeTxt.setText(pointItem.getTime());

            pointsTeamItem.addView(pointsTeamItemLayout);

            if(i == todayMatchList.size() -1)
                seperatorLine.setVisibility(View.GONE);

        }


    }
}
