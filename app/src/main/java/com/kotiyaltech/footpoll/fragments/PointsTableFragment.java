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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.database.PointItem;
import com.kotiyaltech.footpoll.database.PointsTable;
import com.kotiyaltech.footpoll.viewmodel.PointsTableViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointsTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointsTableFragment extends Fragment {

    public static final String TAG = PointsTableFragment.class.getSimpleName();
    private LinearLayout pointsTableContentLayout;

    public PointsTableFragment() {
        // Required empty public constructor
    }

    public static PointsTableFragment newInstance() {
        return new PointsTableFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_points_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pointsTableContentLayout = view.findViewById(R.id.pointsTableContainer);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        final PointsTableViewModel pointsTableViewModel = ViewModelProviders.of(this).get(PointsTableViewModel.class);
        pointsTableViewModel.getPointTable().observe(this, new Observer<PointsTable>() {
            @Override
            public void onChanged(@Nullable PointsTable pointsTable) {
                progressBar.setVisibility(View.GONE);
                if(null != pointsTable) {
                    Map<String, List<PointItem>> groupedPointListMap = pointsTableViewModel.getPointsTableAccordingToGroup(pointsTable.getPointsTable());
                    loadPointsTable(groupedPointListMap);
                }
            }
        });
    }

    private void loadPointsTable(Map<String, List<PointItem>> groupedPointListMap){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        pointsTableContentLayout.removeAllViews();
        for (Map.Entry<String, List<PointItem>> entry : groupedPointListMap.entrySet()) {
            RelativeLayout pointsTableLayout = (RelativeLayout) layoutInflater.inflate(R.layout.point_table_item_layout, null);
            TextView groupNameTv = pointsTableLayout.findViewById(R.id.group_txt);
            groupNameTv.setText("Group " + entry.getKey());

            List<PointItem> pointItemList = entry.getValue();
            Collections.sort(pointItemList);
            for (int i = 0; i < pointItemList.size(); i++) {
                PointItem pointItem = pointItemList.get(i);
                LinearLayout pointsTeamItem = pointsTableLayout.findViewById(R.id.points_team_item_container);
                LinearLayout pointsTeamItemLayout = (LinearLayout) layoutInflater.inflate(R.layout.points_team_iten_layout, null);
                TextView sNo = pointsTeamItemLayout.findViewById(R.id.sNoTxt);
                TextView teamName = pointsTeamItemLayout.findViewById(R.id.teamNameTxt);
                TextView matchPoints = pointsTeamItemLayout.findViewById(R.id.mpTxt);
                TextView win = pointsTeamItemLayout.findViewById(R.id.winTxt);
                TextView loss = pointsTeamItemLayout.findViewById(R.id.lossTxt);
                TextView points = pointsTeamItemLayout.findViewById(R.id.ptsTxt);
                TextView draw = pointsTeamItemLayout.findViewById(R.id.drawTxt);

                sNo.setText(String.valueOf(i + 1));
                teamName.setText(pointItem.getTeamName());
                matchPoints.setText(String.valueOf(pointItem.getMatchesPlayed()));
                win.setText(String.valueOf(pointItem.getWin()));
                loss.setText(String.valueOf(pointItem.getLoss()));
                points.setText(String.valueOf(pointItem.getPoints()));
                draw.setText(String.valueOf(pointItem.getDraw()));
                pointsTeamItem.addView(pointsTeamItemLayout);

            }

            pointsTableContentLayout.addView(pointsTableLayout);
        }
    }
}
