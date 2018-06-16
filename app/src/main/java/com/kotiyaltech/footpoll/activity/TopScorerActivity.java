package com.kotiyaltech.footpoll.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.fragments.TopScorerFragment;

public class TopScorerActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TopScorerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scorer);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                TopScorerFragment.newInstance(), TopScorerFragment.TAG).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
