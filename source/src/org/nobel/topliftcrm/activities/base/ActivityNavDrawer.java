package org.nobel.topliftcrm.activities.base;

import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.HomeActivity;
import org.nobel.topliftcrm.activities.SettingsActivity;
import org.nobel.topliftcrm.activities.base.NavigationListAdapter.NavigationItem;
import org.nobel.topliftcrm.activities.cases.CaseListActivity;
import org.nobel.topliftcrm.activities.contacts.ContactListActivity;
import org.nobel.topliftcrm.activities.deals.DealListActivity;
import org.nobel.topliftcrm.activities.tasks.TaskListActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;

public class ActivityNavDrawer {

    private final ActionBar actionBar;

    private final Activity activity;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    public ActivityNavDrawer(Activity activity, ActionBar actionBar) {
        this.activity = activity;
        this.actionBar = actionBar;
    }

    public void create() {

        drawerList = (ListView) activity.findViewById(R.id.left_drawer);
        drawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long rowId) {
                drawerLayout.closeDrawer(drawerList);
                NavigationItem navigationItem = NavigationItem.getByItemIndex(pos + 1);
                switch (navigationItem) {
                    case HOME:
                        activity.startActivity(new Intent(activity, HomeActivity.class));
                        break;
                    case TASKS:
                        activity.startActivity(new Intent(activity, TaskListActivity.class));
                        break;
                    case CONTACTS:
                        activity.startActivity(new Intent(activity, ContactListActivity.class));
                        break;
                    case DEALS:
                        activity.startActivity(new Intent(activity, DealListActivity.class));
                        break;
                    case CASES:
                        activity.startActivity(new Intent(activity, CaseListActivity.class));
                        break;
                    case SETTINGS:
                        activity.startActivity(new Intent(activity, SettingsActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
        drawerList.setAdapter(new NavigationListAdapter(activity));

        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void syncState() {
        drawerToggle.syncState();
    }

    public void toggleNavDrawer() {

        if (drawerLayout.isDrawerOpen(drawerList)) {
            drawerLayout.closeDrawer(drawerList);
        }
        else {
            drawerLayout.openDrawer(drawerList);
        }

    }
}
