package com.kotiyaltech.footpoll.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.SplashActivity;
import com.kotiyaltech.footpoll.fragments.HomeFragment;
import com.kotiyaltech.footpoll.fragments.PointsTableFragment;
import com.kotiyaltech.footpoll.fragments.ResultsFragment;
import com.kotiyaltech.footpoll.fragments.ScheduleFragment;
import com.kotiyaltech.footpoll.fragments.TodayMatchesFragment;
import com.kotiyaltech.footpoll.fragments.TopScorerFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_INVITE = 0;
    private PointsTableFragment mPointsTableFragment = PointsTableFragment.newInstance();
    private ScheduleFragment mScheduleFragment = ScheduleFragment.newInstance();
    private HomeFragment mHomeFragment = HomeFragment.getInstance();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openHomeFragment();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            mScheduleFragment, ScheduleFragment.TAG).commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            mPointsTableFragment, PointsTableFragment.TAG).commit();
                    return true;
            }
            return false;
        }
    };
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView userImageView = headerView.findViewById(R.id.userImageView);
        TextView userName = headerView.findViewById(R.id.userName);

        Glide.with(this).load(getProfilePicUrl()).into(userImageView);
        userName.setText(mFirebaseUser.getDisplayName());
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        openHomeFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            openHomeFragment();
        } else if (id == R.id.nav_today_match) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    TodayMatchesFragment.newInstance(), TodayMatchesFragment.TAG).commit();
        } else if (id == R.id.nav_result) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    ResultsFragment.newInstance(), ResultsFragment.TAG).commit();
        } else if (id == R.id.nav_top_scorer) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    TopScorerFragment.newInstance(), TopScorerFragment.TAG).commit();
        } else if (id == R.id.nav_logout) {
            logout();
        } else if (id == R.id.nav_invite) {
            sendInvites();
        } else if(id == R.id.nav_rate_us){
            openAppPlayStore();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void sendInvites(){
        Intent intent = new AppInviteInvitation.IntentBuilder("App invitation")
                .setMessage("Foot poll app invitation")
                .setCallToActionText("Install")
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    private void openHomeFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mHomeFragment, HomeFragment.TAG).commit();
    }

    private String getProfilePicUrl(){
        // find the Facebook profile and get the user's id
        for(UserInfo profile : mFirebaseUser.getProviderData()) {
            // check if the provider id matches "facebook.com"
            if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                Uri photoUri = profile.getPhotoUrl();
                if(photoUri != null)
                    return photoUri.toString();
            }
        }
        return "";
    }

    public void openAppPlayStore(){
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

}
