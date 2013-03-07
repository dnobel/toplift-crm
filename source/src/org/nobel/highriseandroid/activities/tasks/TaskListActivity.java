package org.nobel.highriseandroid.activities.tasks;

import java.util.ArrayList;
import java.util.List;

import org.nobel.highriseandroid.activities.base.EntityDetailActivity;
import org.nobel.highriseandroid.activities.base.EntityListActivity;
import org.nobel.highriseandroid.activities.base.EntityListAdapter;
import org.nobel.highriseandroid.data.HighriseApiService;
import org.nobel.highriseapi.entities.Task;
import org.nobel.highriseapi.entities.base.Entity;
import org.nobel.highriseapi.resources.TaskResource;
import org.nobel.highriseapi.resources.TaskResource.TaskList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;


public class TaskListActivity extends EntityListActivity<Task> {

    private static final String SELECTED_TASK_LIST_STATE = "SELECTED_TASK_LIST_STATE";
    private static final String[] TASK_LISTS = new String[] { "Upcoming", "Complete" };
    private TaskList selectedTaskList = TaskList.UPCOMING;

    public TaskList getSelectedTaskList() {
        return selectedTaskList;
    }

    @Override
    protected void clearEntityList() {
        HighriseApiService.getInstance(this).getResource(TaskResource.class).clear();
    }

    @Override
    protected EntityListAdapter<Task> createListAdapter(List<Task> tasks) {
        return new TaskListAdapter(this, tasks);
    }

    @Override
    protected Class<? extends EntityDetailActivity<Task>> getEntityDetailActivity() {
        return TaskDetailActivity.class;
    }

    @Override
    protected List<Task> loadEntityList() {
        TaskResource resource = HighriseApiService.getInstance(this).getResource(TaskResource.class);
        switch (selectedTaskList) {
            case UPCOMING:
                return resource.getUpcoming();
            case COMPLETED:
                return resource.getCompleted();
            default:
                return new ArrayList<Task>();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                com.actionbarsherlock.R.layout.sherlock_spinner_dropdown_item, TASK_LISTS);
        actionBar.setListNavigationCallbacks(spinnerAdapter, new OnNavigationListener() {

            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                selectedTaskList = TaskList.getById(itemPosition);
                loadData(false);
                return true;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_TASK_LIST_STATE)) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TASK_LIST_STATE));
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Entity entity = getListAdapter().getItem(position);
        Intent intent = new Intent(this, getEntityDetailActivity());
        intent.putExtra(EntityDetailActivity.ENTITY_ID, entity.getId());
        intent.putExtra(TaskDetailActivity.TASK_LIST, selectedTaskList.id);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TASK_LIST_STATE, selectedTaskList.id);
    }

}
