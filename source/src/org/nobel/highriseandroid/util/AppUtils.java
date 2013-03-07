package org.nobel.highriseandroid.util;

import org.nobel.highriseandroid.AppConstants;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppUtils {
    public static SharedPreferences getAppPreferences(Activity activity) {
        return activity.getSharedPreferences(AppConstants.PREFERENCES, Context.MODE_PRIVATE);
    }

}
