package com.kotiyaltech.footpoll.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.kotiyaltech.footpoll.BuildConfig;
import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.SplashActivity;
import com.kotiyaltech.footpoll.config.FirebaseConfig;
import com.kotiyaltech.footpoll.fragments.HomeFragment;
import com.kotiyaltech.footpoll.fragments.PointsTableFragment;
import com.kotiyaltech.footpoll.fragments.ScheduleFragment;
import com.kotiyaltech.footpoll.util.Util;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_INVITE = 0;
    private static final String TAG = HomeActivity.class.getSimpleName();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ProfilePictureView userImageView = headerView.findViewById(R.id.userImageView);
        TextView userName = headerView.findViewById(R.id.userName);

        userImageView.setProfileId(Util.getFacebookProfileId(mFirebaseUser));
        userName.setText(mFirebaseUser.getDisplayName());
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        openHomeFragment();
        openAppInvites();
        checkForUpdates();
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
            TodayMatchActivity.startActivity(this);
        } else if (id == R.id.nav_result) {
            ResultActivity.startActivity(this);
        } else if (id == R.id.nav_top_scorer) {
            TopScorerActivity.startActivity(this);
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
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void sendInvites(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download this awesome app FootPoll from play store." + "\n" +
                "https://play.google.com/store/apps/details?id=com.kotiyaltech.footpoll&hl=en&gl=US");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void openHomeFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mHomeFragment, HomeFragment.TAG).commit();
    }

    public void openAppPlayStore(){
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void checkForUpdates() {
        String appVersionCode = FirebaseConfig.getInstance().getConfig().getString(FirebaseConfig.KEY.KEY_VERSION_CODE);
        appVersionCode = TextUtils.isEmpty(appVersionCode) ? "0" : appVersionCode;
        int currAppVersion = Integer.parseInt(appVersionCode);
        if (currAppVersion != 0 && currAppVersion > BuildConfig.VERSION_CODE) {
            String updateMessage = FirebaseConfig.getInstance().getConfig().getString(FirebaseConfig.KEY.KEY_UPDATE_MESSAGE);
            updateMessage = TextUtils.isEmpty(updateMessage) ? "New exciting update available, update your app." : updateMessage;
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(updateMessage)
                    .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            openAppPlayStore();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openAppInvites() {
        // Check for App Invite invitations and launch deep-link activity if possible.
        // Requires that an Activity is registered in AndroidManifest.xml to handle
        // deep-link URLs.
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData data) {
                        if (data == null) {
                            Log.d(TAG, "getInvitation: no data");
                            return;
                        }

                        // Get the deep link
                        Uri deepLink = data.getLink();

                        // Extract invite
                        FirebaseAppInvite invite = FirebaseAppInvite.getInvitation(data);
                        if (invite != null) {
                            String invitationId = invite.getInvitationId();
                        }

                        // Handle the deep link
                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }

}
