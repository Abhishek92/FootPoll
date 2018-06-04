package com.kotiyaltech.footpoll.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.database.VotedUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp pc on 07-04-2018.
 */

public class VotedUserListAdapter extends RecyclerView.Adapter<VotedUserListAdapter.ViewHolder> {

    private List<VotedUser> mVotedUserList = new ArrayList<>();
    private Context mContext;
    public VotedUserListAdapter(List<VotedUser> votedUserList, Context context){
        mVotedUserList = votedUserList;
        mContext = context;
    }
    @Override
    public VotedUserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        VotedUserListAdapter.ViewHolder viewHolder = new VotedUserListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VotedUserListAdapter.ViewHolder holder, int position) {
        VotedUser votedUser = mVotedUserList.get(position);
        Glide.with(mContext).load(votedUser.getImageUrl()).into(holder.profile);
        holder.name.setText(votedUser.getName());
        holder.votedTeam.setText("Voted for: "+votedUser.getTeamVoted());
    }

    @Override
    public int getItemCount() {
        return mVotedUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile;
        TextView name;
        TextView votedTeam;
        public ViewHolder(View view) {
            super(view);
            profile = view.findViewById(R.id.userProfileImg);
            name = view.findViewById(R.id.name);
            votedTeam = view.findViewById(R.id.votedTeam);
        }
    }
}
