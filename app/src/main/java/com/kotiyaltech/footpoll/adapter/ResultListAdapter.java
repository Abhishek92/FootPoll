package com.kotiyaltech.footpoll.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.database.ResultItem;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp pc on 12-06-2018.
 */

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {

    private List<ResultItem> mResultList = new ArrayList<>();
    private Context mContext;

    public ResultListAdapter(List<ResultItem> resultList, Context context){
        mResultList = resultList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_item_layout, parent, false);
        ResultListAdapter.ViewHolder viewHolder = new ResultListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResultItem resultItem = mResultList.get(position);
        Glide.with(mContext).load(resultItem.getTeamOneFlag()).into(holder.teamOneImg);
        Glide.with(mContext).load(resultItem.getTeamTwoFlag()).into(holder.teamTwoImg);
        holder.teamOneName.setText(resultItem.getTeamOne());
        holder.teamTwoName.setText(resultItem.getTeamTwo());
        holder.teamOneGoal.setText(String.valueOf(resultItem.getTeamOneGoal()));
        holder.teamTwoGoal.setText(String.valueOf(resultItem.getTeamTwoGoal()));
        holder.date.setText(resultItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView teamOneImg;
        CircleImageView teamTwoImg;
        TextView teamOneName;
        TextView teamTwoName;
        TextView teamOneGoal;
        TextView teamTwoGoal;
        TextView date;

        ViewHolder(View view) {
            super(view);
            teamOneImg = view.findViewById(R.id.teamOneImg);
            teamTwoImg = view.findViewById(R.id.teamTwoImg);
            teamOneName = view.findViewById(R.id.teamOneTxt);
            teamTwoName = view.findViewById(R.id.teamTwoTxt);
            teamOneGoal = view.findViewById(R.id.teamOneGoal);
            teamTwoGoal = view.findViewById(R.id.teamTwoGoal);
            date = view.findViewById(R.id.dateText);
        }
    }
}
