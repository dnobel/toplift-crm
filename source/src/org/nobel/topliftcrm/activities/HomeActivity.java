package org.nobel.topliftcrm.activities;

import java.util.List;

import org.nobel.highriseapi.entities.Recording;
import org.nobel.highriseapi.resources.RecordingsResource;
import org.nobel.topliftcrm.AppConstants;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.base.EntityListActivity;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;
import org.nobel.topliftcrm.data.HighriseApiService;
import org.nobel.topliftcrm.util.AppUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class HomeActivity extends EntityListActivity<Recording> {
    private static final String NAV_DRAWER_ALREADY_USED = "NAV_DRAWER_ALREADY_USED";
    private static final int TWO_SECONDS = 2000;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.home, menu);
        refreshItem = menu.findItem(R.id.refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                HighriseApiService.logout(getSharedPreferences(AppConstants.PREFERENCES, MODE_PRIVATE));
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void clearEntityList() {
        HighriseApiService.getInstance(this).getResource(RecordingsResource.class).clear();
    }

    @Override
    protected EntityListAdapter<Recording> createListAdapter(List<Recording> entities) {
        return new RecordingListAdapter(this, entities);
    }

    @Override
    protected int getContentView() {
        return R.layout.home;
    }

    @Override
    protected Class<? extends Activity> getEntityDetailActivity() {
        return null;
    }

    @Override
    protected List<Recording> loadEntityList() {
        return HighriseApiService.getInstance(this).getResource(RecordingsResource.class).getAll();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setDivider(null);
        getListView().setDividerHeight(0);
        getListView().getRootView().setBackgroundColor(getResources().getColor(R.color.background));
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getListView().addHeaderView(inflater.inflate(R.layout.recordings_header, null));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // do nothing
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        showNavDrawerIfAppIsUsedTheFirstTime();
    }

    private void setNavDrawerIsAlreadyUsedToTrue(SharedPreferences appPreferences) {
        Editor editor = appPreferences.edit();
        editor.putBoolean(NAV_DRAWER_ALREADY_USED, true);
        editor.commit();
    }

    private void showNavDrawerIfAppIsUsedTheFirstTime() {
        SharedPreferences appPreferences = AppUtils.getAppPreferences(this);
        boolean navDrawerAlreadyUsed = appPreferences.getBoolean(NAV_DRAWER_ALREADY_USED, false);
        if(!navDrawerAlreadyUsed) {
            setNavDrawerIsAlreadyUsedToTrue(appPreferences);
            toggleNavDrawerAfterOneSecond();
        }
    }

    private void toggleNavDrawerAfterOneSecond() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNavDrawer().toggleNavDrawer();
            }
        }, TWO_SECONDS);
    }
}
