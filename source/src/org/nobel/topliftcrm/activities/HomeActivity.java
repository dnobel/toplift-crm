package org.nobel.topliftcrm.activities;

import org.nobel.topliftcrm.AppConstants;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.cases.CaseListActivity;
import org.nobel.topliftcrm.activities.contacts.ContactListActivity;
import org.nobel.topliftcrm.activities.deals.DealListActivity;
import org.nobel.topliftcrm.activities.tasks.TaskListActivity;
import org.nobel.topliftcrm.data.HighriseApiService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class HomeActivity extends SherlockActivity implements OnClickListener {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.taskLink:
                startActivity(new Intent(this, TaskListActivity.class));
                break;
            case R.id.contactLink:
                startActivity(new Intent(this, ContactListActivity.class));
                break;
            case R.id.dealLink:
                startActivity(new Intent(this, DealListActivity.class));
                break;
            case R.id.caseLink:
                startActivity(new Intent(this, CaseListActivity.class));
                break;
            case R.id.settingsLink:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.taskLink).setOnClickListener(this);
        findViewById(R.id.contactLink).setOnClickListener(this);
        findViewById(R.id.dealLink).setOnClickListener(this);
        findViewById(R.id.caseLink).setOnClickListener(this);
        findViewById(R.id.settingsLink).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.home, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
