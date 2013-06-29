package org.nobel.topliftcrm.activities;

import org.nobel.topliftcrm.AppConstants;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.NavigationListAdapter.NavigationItem;
import org.nobel.topliftcrm.activities.cases.CaseListActivity;
import org.nobel.topliftcrm.activities.contacts.ContactListActivity;
import org.nobel.topliftcrm.activities.deals.DealListActivity;
import org.nobel.topliftcrm.activities.tasks.TaskListActivity;
import org.nobel.topliftcrm.data.HighriseApiService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class HomeActivity extends SherlockActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long rowId) {
                drawerLayout.closeDrawer(drawerList);
                NavigationItem navigationItem = NavigationItem.getByItemIndex(pos + 1);
                switch (navigationItem) {
                    case TASKS:
                        startActivity(new Intent(HomeActivity.this, TaskListActivity.class));
                        break;
                    case CONTACTS:
                        startActivity(new Intent(HomeActivity.this, ContactListActivity.class));
                        break;
                    case DEALS:
                        startActivity(new Intent(HomeActivity.this, DealListActivity.class));
                        break;
                    case CASES:
                        startActivity(new Intent(HomeActivity.this, CaseListActivity.class));
                        break;
                    case SETTINGS:
                        startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
        drawerList.setAdapter(new NavigationListAdapter(this));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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

            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(drawerList)) {
                    drawerLayout.closeDrawer(drawerList);
                }
                else {
                    drawerLayout.openDrawer(drawerList);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
