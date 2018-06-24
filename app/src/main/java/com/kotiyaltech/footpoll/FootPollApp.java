package com.kotiyaltech.footpoll;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

/**
 * Created by hp pc on 05-06-2018.
 */

public class FootPollApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }
}
