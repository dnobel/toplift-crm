package org.nobel.highriseandroid.activities;

import org.nobel.highriseandroid.AppConstants;
import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.data.HighriseApiService;
import org.nobel.highriseapi.HighriseClient;
import org.nobel.highriseapi.resources.CaseResource;
import org.nobel.highriseapi.resources.DealResource;
import org.nobel.highriseapi.resources.NoteResource;
import org.nobel.highriseapi.resources.PersonResource;
import org.nobel.highriseapi.resources.TaskResource;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class SettingsActivity extends SherlockPreferenceActivity {

    private final class ClearCacheOnClickListener implements OnPreferenceClickListener {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            getClearCacheDialog().show();
            return true;
        }

        private AlertDialog getClearCacheDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setTitle("Logout").setMessage("Logout from Highrise CRM?");

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    HighriseClient highriseClient = HighriseApiService.getInstance(SettingsActivity.this);
                    highriseClient.getResource(TaskResource.class).clear();
                    highriseClient.getResource(PersonResource.class).clear();
                    highriseClient.getResource(DealResource.class).clear();
                    highriseClient.getResource(CaseResource.class).clear();
                    highriseClient.getResource(NoteResource.class).clear();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            return builder.create();
        }
    }

    private final class LogoutOnClickListener implements OnPreferenceClickListener {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            getLogoutAlertDialog().show();
            return true;
        }

        private AlertDialog getLogoutAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setTitle("Logout").setMessage("Logout from Highrise CRM?");

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    HighriseApiService.logout(getSharedPreferences(AppConstants.PREFERENCES, MODE_PRIVATE));
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                    finish();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            return builder.create();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        addPreferencesFromResource(R.xml.preferences);
        Preference clearCache = findPreference("clearCache");
        clearCache.setOnPreferenceClickListener(new ClearCacheOnClickListener());
        Preference logout = findPreference("logout");
        logout.setOnPreferenceClickListener(new LogoutOnClickListener());
        Preference apiToken = findPreference("apiToken");
        apiToken.setSummary(HighriseApiService.getToken(getSharedPreferences(AppConstants.PREFERENCES, MODE_PRIVATE)));
    }
}
