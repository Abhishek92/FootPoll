package com.kotiyaltech.footpoll.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.database.ScheduleItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleListFragment extends Fragment {


    public static final String KEY_SCHEDULE_LIST = "KEY_SCHEDULE_LIST";

    private ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();
    public ScheduleListFragment() {
        // Required empty public constructor
    }

    public static ScheduleListFragment getInstance(ArrayList<ScheduleItem> scheduleItemList){
        ScheduleListFragment scheduleListFragment = new ScheduleListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_SCHEDULE_LIST, scheduleItemList);
        scheduleListFragment.setArguments(bundle);
        return scheduleListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(KEY_SCHEDULE_LIST)){
            scheduleItems = getArguments().getParcelableArrayList(KEY_SCHEDULE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        LinearLayout scheduleContainer = view.findViewById(R.id.scheduleContainer);
        scheduleContainer.removeAllViews();
        Map<String, List<ScheduleItem>> groupedPointListMap = getPointsTableAccordingToGroup(scheduleItems);
        for (Map.Entry<String, List<ScheduleItem>> entry : groupedPointListMap.entrySet()) {
            RelativeLayout pointsTableLayout = (RelativeLayout) layoutInflater.inflate(R.layout.schedule_item_layout, null);
            TextView dateTv = pointsTableLayout.findViewById(R.id.dateText);
            dateTv.setText(entry.getKey());

            List<ScheduleItem> pointItemList = entry.getValue();
            for (int i = 0; i < pointItemList.size(); i++) {
                ScheduleItem pointItem = pointItemList.get(i);
                LinearLayout pointsTeamItem = pointsTableLayout.findViewById(R.id.schedule_team_item_container);
                RelativeLayout pointsTeamItemLayout = (RelativeLayout) layoutInflater.inflate(R.layout.schedule_team_item_layout, null);
                TextView teamOne = pointsTeamItemLayout.findViewById(R.id.teamOneTxt);
                TextView teamTwo = pointsTeamItemLayout.findViewById(R.id.teamTwoTxt);
                TextView day = pointsTeamItemLayout.findViewById(R.id.dayTxt);
                TextView timeTxt = pointsTeamItemLayout.findViewById(R.id.timeTxt);
                View seperatorLine = pointsTeamItemLayout.findViewById(R.id.seperator_line);


                teamOne.setText(pointItem.getTeamOne());
                teamTwo.setText(pointItem.getTeamTwo());
                day.setText(pointItem.getDay());
                timeTxt.setText(pointItem.getTime());

                pointsTeamItem.addView(pointsTeamItemLayout);

                if(i == pointItemList.size() -1)
                    seperatorLine.setVisibility(View.GONE);

            }

            scheduleContainer.addView(pointsTableLayout);
        }
    }

    public Map<String, List<ScheduleItem>> getPointsTableAccordingToGroup(List<ScheduleItem> pointItemList){
        Map<String, List<ScheduleItem>> groupedTeamMap = new LinkedHashMap<>();
        for (ScheduleItem pointItem : pointItemList) {
            String group = pointItem.getDate();
            if(groupedTeamMap.containsKey(group)){
                List<ScheduleItem> pointItems = groupedTeamMap.get(group);
                pointItems.add(pointItem);
                groupedTeamMap.put(group, pointItems);
            }
            else{
                List<ScheduleItem> pointItems = new ArrayList<>();
                pointItems.add(pointItem);
                groupedTeamMap.put(group, pointItems);
            }
        }

        return groupedTeamMap;
    }
}
