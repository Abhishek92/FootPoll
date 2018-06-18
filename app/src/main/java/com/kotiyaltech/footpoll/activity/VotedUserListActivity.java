package com.kotiyaltech.footpoll.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.adapter.VotedUserListAdapter;
import com.kotiyaltech.footpoll.database.Poll;
import com.kotiyaltech.footpoll.database.VotedUser;

import java.util.List;

public class VotedUserListActivity extends AppCompatActivity {

    public static final String KEY_VOTES_USER_LIST = "KEY_VOTES_USER_LIST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voted_user_list);
        if(null != getSupportActionBar()){
            getSupportActionBar().setTitle("Voted users");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Poll poll = getIntent().getParcelableExtra(KEY_VOTES_USER_LIST);
        List<VotedUser> votedUserList = poll.getVotedUsers();
        RecyclerView userListRecyclerView = findViewById(R.id.user_list_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userListRecyclerView.setLayoutManager(linearLayoutManager);
        VotedUserListAdapter votedUserListAdapter = new VotedUserListAdapter(votedUserList, this);
        userListRecyclerView.setAdapter(votedUserListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
