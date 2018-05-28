package com.kotiyaltech.footpoll.util;

import java.util.List;

/**
 * Created by hp pc on 14-04-2018.
 */

public final class ValidationUtil {
    private ValidationUtil(){

    }

    public static boolean listNotNull(List<?> list){
        return list != null && !list.isEmpty();
    }
}
