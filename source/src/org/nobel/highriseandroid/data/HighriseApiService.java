package org.nobel.highriseandroid.data;

import org.nobel.highriseandroid.util.AppUtils;
import org.nobel.highriseapi.HighriseClient;
import org.nobel.highriseapi.InvalidUserCredentialsException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HighriseApiService {

    private static final String API_TOKEN = "API_TOKEN";

    private static final String HIGH_RISE_BASE_URL = "someBaseUrl";

    private static HighriseClient highriseApi;

    public static void createInstance(SharedPreferences preferences) {
        highriseApi = HighriseClient.create(HIGH_RISE_BASE_URL, preferences.getString(API_TOKEN, null));
        highriseApi.setUseCache(true);
    }

    public static HighriseClient createInstance(String username, String password, SharedPreferences preferences)
            throws InvalidUserCredentialsException {

        highriseApi = HighriseClient.create(HIGH_RISE_BASE_URL);
        highriseApi.setUseCache(true);
        String token = highriseApi.auth(username, password);
        Editor editor = preferences.edit();
        editor.putString(API_TOKEN, token);
        editor.commit();

        return highriseApi;
    }

    public static HighriseClient getInstance(Activity activity) {
        return getInstance(AppUtils.getAppPreferences(activity));
    }

    public static HighriseClient getInstance(SharedPreferences preferences) {
        if (highriseApi == null) {
            createInstance(preferences);
        }
        return highriseApi;
    }

    public static String getToken(SharedPreferences preferences) {
        if (tokenIsPresent(preferences)) {
            return preferences.getString(API_TOKEN, null);
        }
        else {
            return null;
        }
    }

    public static void logout(SharedPreferences preferences) {
        Editor edit = preferences.edit();
        edit.remove(API_TOKEN);
        edit.commit();
        highriseApi = null;
    }

    public static boolean tokenIsPresent(SharedPreferences preferences) {
        return preferences.contains(API_TOKEN);
    }

}
