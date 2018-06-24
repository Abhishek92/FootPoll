package com.kotiyaltech.footpoll.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.database.Poll;
import com.kotiyaltech.footpoll.viewmodel.PollsViewModel;

public class PollResultsActivity extends AppCompatActivity {

    public static final String KEY_POLL_ID = "KEY_POLL_ID";

    private ImageView mTeamOneImageView;
    private ImageView mTeamTwoImageView;
    private TextView mTeamOneNameTxt;
    private TextView mTeamTwoNameTxt;
    private TextView mTeamOneVotePerTxt;
    private TextView mTeamTwoVotePerTxt;
    private TextView mTeamOneTotalVoteTxt;
    private TextView mTeamTwoTotalVoteTxt;
    private TextView mTeamOneTotalVoteDesc;
    private TextView mTeamTwoTotalVoteDesc;
    private TextView mPollQuestion;
    private TextView mSeeVotedUserText;

    public static void startActivity(Context context, int pollId) {
        Intent intent = new Intent(context, PollResultsActivity.class);
        intent.putExtra(PollResultsActivity.KEY_POLL_ID, pollId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_results);
        loadAd();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mTeamOneImageView = findViewById(R.id.teamOne);
        mTeamTwoImageView = findViewById(R.id.teamTwo);

        mPollQuestion = findViewById(R.id.pollsQuestion);
        mTeamOneNameTxt = findViewById(R.id.teamOneTxt);
        mTeamTwoNameTxt = findViewById(R.id.teamTwoTxt);
        mTeamOneTotalVoteTxt = findViewById(R.id.teamOneTotalVote);
        mTeamTwoTotalVoteTxt = findViewById(R.id.teamTwoTotalVote);
        mTeamOneTotalVoteDesc = findViewById(R.id.teamOneTotalVoteTxt);
        mTeamTwoTotalVoteDesc = findViewById(R.id.teamTwoTotalVoteTxt);
        mSeeVotedUserText = findViewById(R.id.seeAllVotedUserTxt);
        mTeamOneVotePerTxt = findViewById(R.id.teamOnePercentage);
        mTeamTwoVotePerTxt = findViewById(R.id.teamTwoPercentage);

        int pollId = getIntent().getIntExtra(KEY_POLL_ID, 0);
        PollsViewModel pollsViewModel = ViewModelProviders.of(this).get(PollsViewModel.class);
        pollsViewModel.getPollById(pollId).observe(this, new Observer<Poll>() {
            @Override
            public void onChanged(@Nullable Poll iplPolls) {
                // progressBar.setVisibility(View.GONE);
                if(null != iplPolls){
                    loadPollAnalytics(iplPolls);
                }
            }
        });
    }

    private void loadPollAnalytics(final Poll poll) {
        Glide.with(this).load(poll.getTeamAFlagUrl()).into(mTeamOneImageView);
        Glide.with(this).load(poll.getTeamBFlagUrl()).into(mTeamTwoImageView);

        mPollQuestion.setText(poll.getQuestion());
        mTeamOneNameTxt.setText(poll.getTeamA());
        mTeamTwoNameTxt.setText(poll.getTeamB());
        mTeamTwoTotalVoteTxt.setText(String.valueOf(poll.getTeamBVotes()));
        mTeamOneTotalVoteTxt.setText(String.valueOf(poll.getTeamAVotes()));
        mTeamOneTotalVoteDesc.setText(String.format("Total votes for %s", poll.getTeamA()));
        mTeamTwoTotalVoteDesc.setText(String.format("Total votes for %s", poll.getTeamB()));

        double total = poll.getTeamAVotes() + poll.getTeamBVotes();
        int teamAPercentage = (int)(total != 0 ? Math.round((double)(poll.getTeamAVotes() * 100) / total) : 0);
        int teamBPercentage = (int)(total != 0 ? Math.round((double)(poll.getTeamBVotes() * 100) / total) : 0);

        mTeamOneVotePerTxt.setText(String.format("%d %s", teamAPercentage, "%"));
        mTeamTwoVotePerTxt.setText(String.format("%d %s", teamBPercentage, "%"));

        mSeeVotedUserText.setText(String.valueOf((int) total));
        mSeeVotedUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PollResultsActivity.this, VotedUserListActivity.class);
                intent.putExtra(VotedUserListActivity.KEY_VOTES_USER_LIST, poll);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadAd() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
