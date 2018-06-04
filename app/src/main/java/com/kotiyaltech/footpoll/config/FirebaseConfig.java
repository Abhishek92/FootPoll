package com.kotiyaltech.footpoll.config;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.kotiyaltech.footpoll.BuildConfig;


public class FirebaseConfig
{
    private static FirebaseConfig ourInstance = new FirebaseConfig();
    private final FirebaseRemoteConfig mFirebaseRemoteConfig;
    private long cacheExpiration = 0;

    private FirebaseConfig()
    {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings =
                new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG).build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
    }

    public static FirebaseConfig getInstance()
    {
        return ourInstance;
    }

    public void refresh()
    {
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(
                    @NonNull
                            Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    // Once the config is successfully fetched it must be activated before newly fetched
                    // values are returned.
                    mFirebaseRemoteConfig.activateFetched();
                }
            }
        });
    }

    public void refresh(OnCompleteListener onCompleteListener)
    {
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(onCompleteListener);
    }

    public FirebaseRemoteConfig getConfig()
    {
        return mFirebaseRemoteConfig;
    }

    public static class KEY
    {
        public static final String KEY_SHARE_LINK = "share_link";
        public static final String KEY_VERSION_CODE = "version_code";
        public static final String KEY_UPDATE_MESSAGE = "update_message";
    }
}
