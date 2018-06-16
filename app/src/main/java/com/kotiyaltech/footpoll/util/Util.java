package com.kotiyaltech.footpoll.util;

import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by hp pc on 14-04-2018.
 */

public final class Util {
    private Util() {

    }

    public static boolean listNotNull(List<?> list){
        return list != null && !list.isEmpty();
    }

    public static String getCurrentTime(DateFormat utcFormat, DateFormat localDateFormat, String source) throws ParseException {
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = utcFormat.parse(source);

        localDateFormat.setTimeZone(TimeZone.getDefault());
        return localDateFormat.format(date);
    }

    public static String getFacebookProfileId(FirebaseUser firebaseUser) {
        // find the Facebook profile and get the user's id
        for (UserInfo profile : firebaseUser.getProviderData()) {
            // check if the provider id matches "facebook.com"
            if (FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                return profile.getUid();
            }
        }
        return "";
    }
}
