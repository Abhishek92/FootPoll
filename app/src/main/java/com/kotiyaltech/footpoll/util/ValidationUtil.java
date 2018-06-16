package com.kotiyaltech.footpoll.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by hp pc on 14-04-2018.
 */

public final class ValidationUtil {
    private ValidationUtil(){

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
}
