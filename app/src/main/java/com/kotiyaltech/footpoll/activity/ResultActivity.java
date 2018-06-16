package com.kotiyaltech.footpoll.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.fragments.ResultsFragment;

public class ResultActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ResultActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                ResultsFragment.newInstance(), ResultsFragment.TAG).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
