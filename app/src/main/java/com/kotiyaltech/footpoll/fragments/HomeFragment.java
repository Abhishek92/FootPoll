package com.kotiyaltech.footpoll.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.share.widget.ShareButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.activity.PollResultsActivity;
import com.kotiyaltech.footpoll.database.Poll;
import com.kotiyaltech.footpoll.database.Polls;
import com.kotiyaltech.footpoll.database.Response;
import com.kotiyaltech.footpoll.database.VotedUser;
import com.kotiyaltech.footpoll.util.ValidationUtil;
import com.kotiyaltech.footpoll.viewmodel.PollsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private Polls mIplPolls;
    private PollsViewModel pollsViewModel;
    private LinearLayout mPollContainer;
    private FirebaseUser mFirebaseUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(){
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPollContainer = view.findViewById(R.id.pollsContainer);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        pollsViewModel = ViewModelProviders.of(this).get(PollsViewModel.class);
        pollsViewModel.getPolls().observe(this, new Observer<Polls>() {
            @Override
            public void onChanged(@Nullable Polls iplPolls) {
                progressBar.setVisibility(View.GONE);
                if(null != iplPolls){
                    loadPolls(iplPolls);
                }
            }
        });
    }

    private void loadPolls(Polls iplPolls) {
        mIplPolls = iplPolls;
        List<Poll> pollList = iplPolls.getPolls();
        mPollContainer.removeAllViews();
        if(ValidationUtil.listNotNull(pollList)) {
            for (int i = 0; i < pollList.size(); i++) {
                final Poll poll = pollList.get(i);
                CardView pollsLayout = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.polls_layout, null);
                TextView mPollQuestion = pollsLayout.findViewById(R.id.pollsQuestion);
                ImageView teamAFlagImg = pollsLayout.findViewById(R.id.teamOneImg);
                ImageView teamBFlagImg = pollsLayout.findViewById(R.id.teamTwoImg);
                TextView mTeamAPercentage = pollsLayout.findViewById(R.id.teamAWinPercentage);
                TextView mTeamBPercentage = pollsLayout.findViewById(R.id.teamBWinPercentage);
                TextView alreadyVotedText = pollsLayout.findViewById(R.id.alreadyVotedTxt);
                TextView userListText = pollsLayout.findViewById(R.id.userList);
                RadioButton mTeamARadioBtn = pollsLayout.findViewById(R.id.teamARb);
                RadioButton mTeamBRadioBtn = pollsLayout.findViewById(R.id.teamBRb);
                TextView teamAName = pollsLayout.findViewById(R.id.teamOneName);
                TextView teamBName = pollsLayout.findViewById(R.id.teamTwoName);
                Button mVoteBtn = pollsLayout.findViewById(R.id.voteBtn);
                RadioGroup pollRadioGroup = pollsLayout.findViewById(R.id.pollRg);
                TextView pollResults = pollsLayout.findViewById(R.id.pollResults);
               // ShareButton deviceShareButton = pollsLayout.findViewById(R.id.fbShare);

                Glide.with(this).load(poll.getTeamAFlagUrl()).into(teamAFlagImg);
                Glide.with(this).load(poll.getTeamBFlagUrl()).into(teamBFlagImg);

                pollResults.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), PollResultsActivity.class);
                        intent.putExtra(PollResultsActivity.KEY_POLL_ID, poll.getId());
                        startActivity(intent);
                    }
                });
                mVoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LinearLayout linearLayout = (LinearLayout) view.getParent().getParent();
                        setVotingData(linearLayout);
                    }
                });

                userListText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       /* Intent intent = new Intent(getActivity(), VotedUserListActivity.class);
                        intent.putExtra(VotedUserListActivity.KEY_VOTES_USER_LIST, poll);
                        startActivity(intent);*/
                    }
                });

                boolean isAlreadyVoted = checkIfUserAlreadyVoted(poll.getVotedUsers());
               // deviceShareButton.setVisibility(!isAlreadyVoted ? View.GONE : View.VISIBLE);
                enableDisableRadioButton(isAlreadyVoted, pollRadioGroup);
                mVoteBtn.setVisibility(!isAlreadyVoted ? View.VISIBLE : View.GONE);
                VotedUser votedUser = getCurrentVotedUser(poll.getVotedUsers());

                if (votedUser != null) {
                    alreadyVotedText.setText(String.format("You've voted for %s", votedUser.getTeamVoted()));
                   // createShareContent(deviceShareButton, votedUser.getTeamVoted());
                }
                alreadyVotedText.setVisibility(isAlreadyVoted ? View.VISIBLE : View.GONE);

                mPollQuestion.setText(poll.getQuestion());
                teamAName.setText(poll.getTeamA());
                teamBName.setText(poll.getTeamB());

                double total = poll.getTeamAVotes() + poll.getTeamBVotes();
                int teamAPercentage = (int)(total != 0 ? Math.round((double)(poll.getTeamAVotes() * 100) / total) : 0);
                int teamBPercentage = (int)(total != 0 ? Math.round((double)(poll.getTeamBVotes() * 100) / total) : 0);

                mTeamAPercentage.setText(String.format("%s: %d %s", poll.getTeamA(), teamAPercentage, "%"));
                mTeamBPercentage.setText(String.format("%s: %d %s", poll.getTeamB(), teamBPercentage, "%"));

                pollsLayout.setTag(i);
                mPollContainer.addView(pollsLayout);

            }
        }
    }

    private void setVotingData(LinearLayout linearLayout) {
        int position = (int) linearLayout.getTag();
        RadioGroup mPollRadioGroup = linearLayout.findViewById(R.id.pollRg);
        int selectedRadioButtonId = mPollRadioGroup.getCheckedRadioButtonId();
        if(selectedRadioButtonId == -1){
            Toast.makeText(getActivity(), "Choose your favourite team first", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedRadioButton = mPollRadioGroup.findViewById(selectedRadioButtonId);
        Poll poll = mIplPolls.getPolls().get(position);
        VotedUser votedUser = new VotedUser();
        votedUser.setTeamVoted(selectedRadioButton.getText().toString());
        votedUser.setEmail(mFirebaseUser.getEmail());
        votedUser.setName(mFirebaseUser.getDisplayName());
        votedUser.setImageUrl(getProfilePicUrl());
        votedUser.setUId(mFirebaseUser.getUid());
        poll.addVotedUsers(votedUser);
        switch (selectedRadioButtonId){
            case R.id.teamARb:
                poll.voteForTeamA();
                break;
            case R.id.teamBRb:
                poll.voteForTeamB();
                break;
            default:
                break;
        }
        saveIplPollsData();
    }

    private void saveIplPollsData() {
        pollsViewModel.savePollsData(mIplPolls).observe(this, new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                if(null != response){
                    if(response.isSuccess())
                        Toast.makeText(getContext(), "Vote submitted", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getContext(), "Vote not submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void enableDisableRadioButton(boolean isAlreadyVoted, RadioGroup radioGroup){
        int childCount = radioGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            radioGroup.getChildAt(i).setEnabled(!isAlreadyVoted);
        }
    }

    private void createShareContent(ShareButton deviceShareButton, String teamName){
       /* ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(FirebaseConfig.getInstance().getConfig().getString(FirebaseConfig.KEY.KEY_SHARE_LINK)))
                .setQuote("I've voted for my favourite team "+teamName)
                .build();
        deviceShareButton.setShareContent(content);*/

    }

    private boolean checkIfUserAlreadyVoted(List<VotedUser> votedUserList){
        VotedUser votedUser = new VotedUser();
       // votedUser.setUId(mFirebaseUser.getUid());
        return votedUserList.contains(votedUser);
    }

    private VotedUser getCurrentVotedUser(List<VotedUser> votedUserList){
       /* VotedUser votedUser = new VotedUser();
        votedUser.setEmail(mFirebaseUser.getEmail());
        votedUser.setUId(mFirebaseUser.getUid());
        int index = votedUserList.indexOf(votedUser);
        if(index != -1)
            return votedUserList.get(index);
        else*/ return null;
    }

    private String getProfilePicUrl(){
        // find the Facebook profile and get the user's id
        /*for(UserInfo profile : mFirebaseUser.getProviderData()) {
            // check if the provider id matches "facebook.com"
            if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                Uri photoUri = profile.getPhotoUrl();
                if(photoUri != null)
                    return photoUri.toString();
            }
        }*/
        return "";
    }


}
