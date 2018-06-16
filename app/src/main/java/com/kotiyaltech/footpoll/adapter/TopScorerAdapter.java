package com.kotiyaltech.footpoll.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.database.TopScorerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 15-06-2018.
 */

public class TopScorerAdapter extends RecyclerView.Adapter<TopScorerAdapter.ViewHolder> {

    private List<TopScorerItem> topScorerItems = new ArrayList<>();
    private Context mContext;

    public TopScorerAdapter(List<TopScorerItem> resultList, Context context) {
        topScorerItems = resultList;
        mContext = context;
    }

    @Override
    public TopScorerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_scorer_item_layout, parent, false);
        TopScorerAdapter.ViewHolder viewHolder = new TopScorerAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopScorerAdapter.ViewHolder holder, int position) {
        TopScorerItem topScorerItem = topScorerItems.get(position);
        Glide.with(mContext).load(topScorerItem.getTeamFlag()).into(holder.teamImg);
        holder.playerName.setText(topScorerItem.getPlayerName());
        holder.countryName.setText(topScorerItem.getTeamName());
        holder.goal.setText(topScorerItem.getGoals());
        holder.assist.setText(topScorerItem.getAssist());
        holder.minutesPlayed.setText(topScorerItem.getMinutesPlayed());
    }

    @Override
    public int getItemCount() {
        return topScorerItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView teamImg;
        TextView playerName;
        TextView countryName;
        TextView goal;
        TextView assist;
        TextView minutesPlayed;

        ViewHolder(View view) {
            super(view);
            teamImg = view.findViewById(R.id.countryFlag);
            playerName = view.findViewById(R.id.playerName);
            countryName = view.findViewById(R.id.countryName);
            goal = view.findViewById(R.id.goal);
            assist = view.findViewById(R.id.assist);
            minutesPlayed = view.findViewById(R.id.minutesPlayed);
        }
    }
}

